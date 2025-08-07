package org.dromara.kb.folderFile.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dromara.com.user.domain.ComUser;
import org.dromara.com.user.domain.vo.ComUserVo;
import org.dromara.com.user.mapper.ComUserMapper;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.kb.es.service.imp.FileESServiceImp;
import org.dromara.kb.fileContent.domain.FileContent;
import org.dromara.kb.fileContent.domain.bo.FileContentBo;
import org.dromara.kb.fileContent.domain.vo.FileContentVo;
import org.dromara.kb.fileContent.mapper.FileContentMapper;
import org.dromara.kb.fileContent.service.IFileContentService;
import org.dromara.kb.fileRecently.domain.FileRecently;
import org.dromara.kb.fileRecently.mapper.FileRecentlyMapper;
import org.dromara.kb.fileRecently.service.impl.FileRecentlyServiceImpl;
import org.dromara.kb.fileShare.domain.FileShare;
import org.dromara.kb.fileTemplate.domain.FileTemplate;
import org.dromara.kb.fileTemplate.domain.bo.FileTemplateBo;
import org.dromara.kb.fileTemplate.domain.vo.FileTemplateVo;
import org.dromara.kb.fileTemplate.mapper.FileTemplateMapper;
import org.dromara.kb.fileTemplate.service.IFileTemplateService;
import org.dromara.kb.folderFile.domain.bo.FolderFileRenameBo;
import org.dromara.kb.folderFile.domain.bo.FolderFileTemplateBo;
import org.dromara.kb.folderFile.domain.vo.ESFolderFileVo;
import org.dromara.kb.km.domain.Km;
import org.dromara.kb.km.mapper.KmMapper;
import org.dromara.kb.recycle.domain.Recycle;
import org.dromara.kb.recycle.domain.bo.RecycleBo;
import org.dromara.kb.recycle.mapper.RecycleMapper;
import org.dromara.kb.recycle.service.IRecycleService;
import org.dromara.kb.searchHistory.domain.SearchHistory;
import org.dromara.kb.searchHistory.domain.vo.SearchHistoryVo;
import org.dromara.kb.searchHistory.mapper.SearchHistoryMapper;
import org.dromara.kb.utils.common.ErrorMsg;
import org.dromara.system.domain.bo.SysDictDataBo;
import org.dromara.system.domain.vo.SysDictDataVo;
import org.dromara.system.service.ISysDictDataService;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.common.text.Text;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.dromara.kb.folderFile.domain.bo.FolderFileBo;
import org.dromara.kb.folderFile.domain.vo.FolderFileVo;
import org.dromara.kb.folderFile.domain.FolderFile;
import org.dromara.kb.folderFile.mapper.FolderFileMapper;
import org.dromara.kb.folderFile.service.IFolderFileService;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件夹&文件Service业务层处理
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@RequiredArgsConstructor
@Service
@EnableAsync
public class FolderFileServiceImpl implements IFolderFileService {

    private final FolderFileMapper baseMapper;

    private final FileContentMapper fileContentMapper;

    private final IRecycleService recycleService;

    private final FileTemplateMapper fileTemplateMapper;

    private final KmMapper kmMapper;

    private final ComUserMapper comUserMapper;

    private final ISysDictDataService iSysDictDataService;

    private final FileESServiceImp fileESServiceImp;

    private final ObjectMapper objectMapper;

    private final SearchHistoryMapper searchHistoryMapper;

    private final IFileContentService fileContentService;

    private final FileRecentlyMapper fileRecentlyMapper;


    /**
     * 新增文件夹&文件
     * 1. 预检查
     *     1.1 文件名检查：文件名规范处理字符处理
     *     1.2 检查同一目录是否有重名
     * 2. 文件处理
     *     2.1 创建文件记录
     *       2.1.1 是否开启AI处理
     *     2.2 所在目录处理：根目录（文件夹："/"+当前文件夹ID，文件：空）
     *     2.3 所在目录处理：子目录（文件夹：父文件夹ids+"/"+当前文件夹ID，文件：父文件夹ids）
     *     2.4 创建文件内容记录
     *     2.5 检查是否是复制
     * 3. 知识文档树结构处理（若fileKmTreeData不为空）
     * 4. 同步ES存储（非文件夹）
     *
     * @param bo FolderFileBo
     * @author moks.mo
     */
    @Transactional
    @Override
    public R<Long> insertByBo(FolderFileBo bo) {
        FolderFile folderFile = MapstructUtils.convert(bo, FolderFile.class);
        folderFile.setLinkUserId(LoginHelper.getLoginUser().getUserId());

        // 1. 预检查
        // 1.1 文件名检查：文件名规范处理字符处理
        String  fileNamePatternRule = "^[\\\\/'\"“”]+$";
        Pattern fileNamePattern     = Pattern.compile(fileNamePatternRule);
        Matcher fileNameMatcher     = fileNamePattern.matcher(bo.getFileName());
        if(fileNameMatcher.find()){
            return R.fail(ErrorMsg.ERR_KB_FILE_NAME_ILLEGAL.getMessage());
        }

        // 1.2 检查同一目录是否有重名
        long fileCount = baseMapper.selectCount(new LambdaQueryWrapper<FolderFile>()
            .eq(FolderFile::getLinkKmId,bo.getLinkKmId())
            .eq(FolderFile::getParentId,bo.getParentId())
            .eq(FolderFile::getStatus,0)
            .eq(FolderFile::getFileName,bo.getFileName()));
        if(fileCount>0){
            return R.fail(ErrorMsg.ERR_KB_FILE_NAME_DOUBLE.getMessage());
        }

        // 2. 文件处理
        long isFolder = bo.getLinkFileTypeCode().equals("folder")?1:0;

        // 目录ids，存储方便进行批量删除
        String ids = "";

        // 2.1 创建文件记录
        folderFile.setEditing(0);
        folderFile.setIsFolder(isFolder);
        // 2.1.1 是否开启AI处理
        Km kmInfo = kmMapper.selectById(bo.getLinkKmId());
        if(kmInfo.getIsAiOpen()==1){
            folderFile.setAiStatus(1);
        }

        // 扩展名（附件使用参数附件后缀名，其他使用设置文件类型编码）
        String fileExtension = bo.getLinkFileTypeCode().equals("attachment")?bo.getFileExtension():bo.getLinkFileTypeCode();
        folderFile.setFileExtension(fileExtension);
        baseMapper.insert(folderFile);
        bo.setId(folderFile.getId());

        // 2.2 所在目录处理：根目录（文件夹："/"+当前文件夹ID，文件：空）
        if(bo.getParentId()==0){
            if(isFolder==1){
                ids="/"+folderFile.getId();
            }
        }else{
            // 2.3 所在目录处理：子目录（文件夹：父文件夹ids+"/"+当前文件夹ID，文件：父文件夹ids）
            FolderFileVo parentFolderFileVo = baseMapper.selectVoById(bo.getParentId());
            String parentFolderCatalogIds   =  parentFolderFileVo.getCatalogIds();
            if(isFolder == 1){
                ids = parentFolderCatalogIds+"/"+folderFile.getId();
            }else{
                ids = parentFolderCatalogIds;
            }
        }

        folderFile.setCatalogIds(ids);
        baseMapper.updateById(folderFile);

        // 2.4 创建文件内容记录
        if(isFolder==0){
            FileContent fileContent = new FileContent();
            fileContent.setLinkFileId(folderFile.getId());
            fileContent.setLinkKmId(folderFile.getLinkKmId());
            fileContent.setCatalogIds(ids);

            // 2.5 检查是否是复制
            if(StringUtils.isNotEmpty(bo.getCopyFileId())){
                FolderFile folderFileCopySourceFile = baseMapper.selectById(Long.parseLong(bo.getCopyFileId()));
                FileContent fileContentSourceFileContent = fileContentMapper.selectById(folderFileCopySourceFile.getLinkFileContentId());
                fileContent.setLinkFileContent(fileContentSourceFileContent.getLinkFileContent());
            }

            fileContentMapper.insert(fileContent);
            // 更新文件内容ID
            folderFile.setLinkFileContentId(fileContent.getId());
            baseMapper.updateById(folderFile);
        }



        // 3. 知识文档树结构处理（若fileKmTreeData不为空）
        if(StringUtils.isNotEmpty(bo.getFileKmTreeData())){
            Km km = new Km();
            km.setId(bo.getLinkKmId());
            km.setFileKmTreeData(bo.getFileKmTreeData());
            kmMapper.updateById(km);
        }

        // 4. 同步ES存储（非文件夹）
        if(!"folder".equals(folderFile.getLinkFileTypeCode())){
            asyncInsertFileESData(folderFile);
        }

        return R.ok(folderFile.getId());
    }


    @Async
    public void asyncInsertFileESData(FolderFile folderFile){
        fileESServiceImp.insertFileESData(folderFile);
    }

    /**
     * 使用模版创建文件
     *
     * 1. 获取模版信息
     * 2. 获取模版文件映射的文件和内容
     * 3. 调用创建文档服务
     * 4. 调用文档内容服务
     * 5. 更新模版使用次数
     * */
    @Override
    public R<FolderFileVo> insertByTemplateBo(FolderFileTemplateBo bo) {

        // 1. 获取模版信息
        FileTemplateVo fileTemplateVo = fileTemplateMapper.selectVoById(bo.getTemplateId());

        // 2. 获取模版文件映射的文件和内容
        FolderFile mappingTemplateFile = baseMapper.selectById(Long.parseLong(fileTemplateVo.getAttrContent()));
        FileContent mappingTemplateFileContent = fileContentMapper.selectById(mappingTemplateFile.getLinkFileContentId());

        // 3. 调用创建文档服务
        FolderFileBo folderFileBo = new FolderFileBo();
        folderFileBo.setIsFolder(0L);
        folderFileBo.setEditing(0);
        folderFileBo.setFileName("XXXX_"+ System.currentTimeMillis());
        folderFileBo.setLinkFileTypeCode(fileTemplateVo.getFileTypeCode());
        folderFileBo.setLinkFileTypeName(fileTemplateVo.getFileTypeName());
        folderFileBo.setParentId(bo.getParentId());
        folderFileBo.setLinkKmId(bo.getLinkKmId());
        insertByBo(folderFileBo);

        // 4. 调用文档内容服务
        FileContentBo fileContentBo = new FileContentBo();
        fileContentBo.setLinkFileId(folderFileBo.getId());
        String content = mappingTemplateFileContent.getLinkFileContent();
        if(null == content || StringUtils.isEmpty(content) || "null".equals(content)){
            content = "";
        }
        fileContentBo.setLinkFileContent(content);
        fileContentService.updateByBo(fileContentBo);

        //  5. 更新模版使用次数
        FileTemplate fileTemplate = new FileTemplate();
        fileTemplate.setId(Long.parseLong(bo.getTemplateId()));
        fileTemplate.setUseCount(fileTemplateVo.getUseCount()+1);
        fileTemplateMapper.updateById(fileTemplate);

        FolderFileVo rtFolderFileVo = new FolderFileVo();
        rtFolderFileVo.setId(folderFileBo.getId());

        return R.ok(rtFolderFileVo);
    }

    /**
     * 查询文件夹&文件
     */
    @Override
    public FolderFileVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询分享文件夹&文件
     * 1. 查询知识库分享信息
     * 2. 返回分享文件信息
     */
    @Override
    public FolderFileVo queryShareFolderFileByShareId(String shareId,Long fileId){
        // 1. 查询知识库分享信息
        // 2. 返回分享文件信息
        FolderFileVo folderFileVo = baseMapper.selectVoById(fileId);

        // 填充内容
        FileContentVo fileContentVo = fileContentMapper.selectVoById(folderFileVo.getLinkFileContentId());
        folderFileVo.setLinkFileContent(fileContentVo.getLinkFileContent());
        return folderFileVo;
    }

    /**
     * 查询文件夹&文件列表
     * 1. 查询数据
     * 2. 填充
     */
    @Override
    public TableDataInfo<FolderFileVo> queryPageList(FolderFileBo bo, PageQuery pageQuery) {

        // 参数检查
        if(null == bo.getLinkKmId()){
            return TableDataInfo.build(new Page<FolderFileVo>());
        }

        // 1. 查询数据
        LambdaQueryWrapper<FolderFile> lqw = buildQueryWrapper(bo);
        Page<FolderFileVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);

        // 目录无数据
        if(result.getTotal()==0){
            return TableDataInfo.build(result);
        }

        // 2. 填充

        // 字典：图标信息
        SysDictDataBo dictDataFileTypeBo = new SysDictDataBo();
        dictDataFileTypeBo.setDictType("file_type");
        List<SysDictDataVo> fileTypeList = iSysDictDataService.selectDictDataList(dictDataFileTypeBo);

        // 字典：AI 状态
        SysDictDataBo dictDataAiStatusTypeBo = new SysDictDataBo();
        dictDataAiStatusTypeBo.setDictType("ai_status");
        List<SysDictDataVo> aiStatusList = iSysDictDataService.selectDictDataList(dictDataAiStatusTypeBo);


        // 用户信息列表
        List<Long> userIdsBefore = new ArrayList<Long>();
        for(FolderFileVo folderFileVo:result.getRecords()){
            userIdsBefore.add(Long.parseLong(folderFileVo.getCreateBy()));
            boolean isUpdateByEmpty = StringUtils.isNotEmpty(folderFileVo.getCreateBy());
            String updateBy = isUpdateByEmpty?"0":folderFileVo.getUpdateBy();
            userIdsBefore.add(Long.parseLong(updateBy));
        }

        List<Long> userIds = new LinkedList<>(new TreeSet<>(userIdsBefore));
        List<ComUserVo> comUserList = comUserMapper.selectVoList(new LambdaQueryWrapper<ComUser>().in(ComUser::getId, userIds));


        for(FolderFileVo folderFileVo:result.getRecords()){
            // 填充创建人和修改人
            for(ComUserVo comUserVo:comUserList){
                // 创建人
                if(folderFileVo.getCreateBy().equals(""+comUserVo.getId())){
                    folderFileVo.setCreateBy(comUserVo.getUserName());
                }

                // 修改人
                if(null != folderFileVo.getUpdateBy() && folderFileVo.getUpdateBy().equals(""+comUserVo.getId())){
                    folderFileVo.setUpdateBy(comUserVo.getUserName());
                }
            }

            // 填充图标
            for(SysDictDataVo sysDictDataVo:fileTypeList){
                if(folderFileVo.getLinkFileTypeCode().equals(sysDictDataVo.getDictValue())){
                    JSONObject jsonObject = JSONUtil.parseObj(sysDictDataVo.getRemark());
                    folderFileVo.setFileIcon(jsonObject.getStr("icon"));
                    folderFileVo.setFileIconColor(jsonObject.getStr("color"));
                    break;
                }
            }

            // 填充AI 状态
            String aiStatus = ""+folderFileVo.getAiStatus();
            String fileType = folderFileVo.getFileExtension();

            if(fileType.equals("pdf") || fileType.equals("markdown")){
                for(SysDictDataVo aiStatusDictVo:aiStatusList){
                    if(aiStatusDictVo.getDictValue().equals(aiStatus)){
                        folderFileVo.setAiStatusLabel(aiStatusDictVo.getDictLabel());
                    }
                }
            }else{
                folderFileVo.setAiStatusLabel("-");
            }
        }

        return TableDataInfo.build(result);
    }


    /**
     * 查询最近编辑文件
     * 1. 获取当前登陆用户ID
     * 2. 查询最近文件
     */
    @Override
    public TableDataInfo<FolderFileVo> queryPageRecentlyEdit(FolderFileBo bo, PageQuery pageQuery) {

        // 1. 获取当前登陆用户ID
        LoginUser loginUser = LoginHelper.getLoginUser();
        long userId = loginUser.getUserId();

        // 2. 查询最近文件
        LambdaQueryWrapper<FolderFile> lqw = buildQueryWrapper(bo);
        bo.setIsFolder(0L);
        bo.setUpdateBy(userId);
        pageQuery.setOrderByColumn("updateTime");
        pageQuery.setIsAsc("desc");
        Page<FolderFileVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询文件夹&文件列表
     */
    @Override
    public List<FolderFileVo> queryList(FolderFileBo bo) {
        LambdaQueryWrapper<FolderFile> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolderFile> buildQueryWrapper(FolderFileBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolderFile> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getLinkKmId() != null, FolderFile::getLinkKmId, bo.getLinkKmId());
        lqw.eq(FolderFile::getStatus, 0);
        lqw.like(StringUtils.isNotEmpty(bo.getFileName()),FolderFile::getFileName,bo.getFileName());

        if(null == bo.getParentId()){
            lqw.eq(true, FolderFile::getParentId,0);
        }else{
            lqw.eq(true, FolderFile::getParentId, bo.getParentId());
        }
        return lqw;
    }



    /**
     * 重命名
     * 1. 重命名文件
     * 2. 更新最近编辑记录
     * 3. 更新ES数据
     *
     * @author moks.mo
     */
    @Override
    public Boolean updateByBo(FolderFileRenameBo bo) {
        FolderFile update = MapstructUtils.convert(bo, FolderFile.class);
        validEntityBeforeSave(update);

        // 1.1 文件名检查：文件名规范处理字符处理
        String  fileNamePatternRule = "^[\\\\/'\"“”]+$";
        Pattern fileNamePattern     = Pattern.compile(fileNamePatternRule);
        Matcher fileNameMatcher     = fileNamePattern.matcher(bo.getFileName());
        if(fileNameMatcher.find()){
            return false;
        }

        // 1. 重命名文件
        int updateCount = baseMapper.updateById(update);

        // 2. 更新最近编辑记录
        asyncRecentlyRename(update);

        // 3. 更新ES数据
        asyncRenameFileESData(update);

        return updateCount > 0;
    }

    @Async
    public void asyncRecentlyRename(FolderFile folderFile){
        LambdaUpdateWrapper<FileRecently> lqw = Wrappers.lambdaUpdate();
        lqw.set(FileRecently::getFileName,folderFile.getFileName());
        lqw.eq(FileRecently::getLinkFileId,folderFile.getId());
        fileRecentlyMapper.update(lqw);
    }


    @Async
    public void asyncRenameFileESData(FolderFile folderFile){
        fileESServiceImp.renameFileESData(folderFile);
    }


    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolderFile entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除文件夹&文件
     * 1. 删除文件
     *     1.1 新增回收站记录
     *     1.2 删除单个文件 (保存回收站ID，支持恢复及彻底删除)
     *     1.3 删除文件夹夹或知识文档 (保存回收站ID，支持恢复及彻底删除)
     *     1.4 删除文件内容 (保存回收站ID，支持恢复及彻底删除)
     * 2. 删除最近编辑记录
     *
     * @param ids 文件ID
     * @param isCreateCycle 是否创建回收站记录，若false，需传入cycleId
     * @param cycleId 回收站ID
     * @author moks.mo
     */
    @Transactional
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isCreateCycle, Long cycleId) {

        // 1. 删除文件
        Iterator<Long> it = ids.iterator();
        while (it.hasNext()){

            long fileId = it.next();
            FolderFile folderFile = baseMapper.selectById(fileId);
            long isFolder         = folderFile.getIsFolder();
            String fileName       = folderFile.getFileName();
            String catalogIds     = folderFile.getCatalogIds();
            String type           = (isFolder==0 ? "file":"folder");
            String fileKmTreeData = folderFile.getFileKmTreeData();

            // 1.1 新增回收站记录
            if(false!=isCreateCycle){
                RecycleBo recycleBo = new RecycleBo();
                recycleBo.setType(type);
                recycleBo.setLinkName(fileName);
                recycleBo.setLinkId(fileId);
                recycleBo.setLinkFileTypeCode(folderFile.getLinkFileTypeCode());
                recycleBo.setLinkFileTypeName(folderFile.getLinkFileTypeName());
                recycleBo.setLinkKmId(folderFile.getLinkKmId());
                recycleService.insertByBo(recycleBo);
                cycleId = recycleBo.getId();
            }


            // 非文件夹&非知识文档
            if(0==isFolder && !StringUtils.isNotEmpty(fileKmTreeData)){
                // 1.2 删除单个文件 (保存回收站ID，支持恢复及彻底删除)
                FolderFile updateFolderFile = new FolderFile();
                updateFolderFile.setId(folderFile.getId());
                updateFolderFile.setLinkCycleId(cycleId);
                updateFolderFile.setStatus(1);
                baseMapper.updateById(updateFolderFile);

                // 1.4 删除文件内容 (保存回收站ID，支持恢复及彻底删除)
                FileContent fileContent = fileContentMapper.selectOne(new LambdaQueryWrapper<FileContent>()
                    .eq(FileContent::getLinkFileId,folderFile.getId()));
                fileContent.setLinkCycleId(cycleId);
                fileContent.setStatus(1);
                fileContentMapper.updateById(fileContent);

            }else{
                // 1.3 删除文件夹夹或知识文档 (保存回收站ID，支持恢复及彻底删除)
                // 删除当前文件夹
                folderFile.setStatus(1);
                baseMapper.updateById(folderFile);

                // 删除文件夹子目录及文件
                baseMapper.deleteByFolderId(catalogIds+"%",cycleId);

                // 1.4 删除文件内容 (保存回收站ID，支持恢复及彻底删除)
                fileContentMapper.deleteByFolderId(catalogIds+"%",cycleId);

            }

            // 2. 删除最近编辑记录
            LambdaQueryWrapper<FileRecently> fileRecentlyDeleteWrapper = Wrappers.lambdaQuery();
            fileRecentlyDeleteWrapper.eq(FileRecently::getLinkFileId, fileId);
            fileRecentlyMapper.delete(fileRecentlyDeleteWrapper);

        }

        return true;
    }



    /**
     * ES文档查询
     * 1. ES查询
     * 2. 记录搜索历史
     * */
    @Override
    public String fileESSearch(String key){
        String rt = "";

//        // 1. ES查询
//        List<ESFolderFileVo> list = new ArrayList<ESFolderFileVo>();
//        SearchResponse searchResponse = fileESServiceImp.searchFileESData(key);
//        System.out.println("searchResponse="+searchResponse);
//        for (SearchHit hit : searchResponse.getHits().getHits()) {
//            ESFolderFileVo esFolderFileVo = new ESFolderFileVo();
//            esFolderFileVo.setFileId(hit.getSourceAsMap().get("fileId").toString());
//            esFolderFileVo.setIcon(hit.getSourceAsMap().get("fileIcon").toString());
//            esFolderFileVo.setColor(hit.getSourceAsMap().get("fileIconColor").toString());
//            esFolderFileVo.setCreateBy(hit.getSourceAsMap().get("createBy").toString());
//            esFolderFileVo.setLastUpdateTime(hit.getSourceAsMap().get("lastUpdateTime").toString());
//            esFolderFileVo.setLinkFileTypeCode(hit.getSourceAsMap().get("linkFileTypeCode").toString());
//            esFolderFileVo.setLinkFileTypeName(hit.getSourceAsMap().get("linkFileTypeName").toString());
//
//            if(null != hit.getHighlightFields().get("content")){
//                String content = hit.getHighlightFields().get("content").getFragments()[0].toString();
//                esFolderFileVo.setFileContent(content.length()>100?content.substring(0,100):content);
//            }
//
//            if(null != hit.getHighlightFields().get("fileName")){
//                // 高亮处理
//                String fileName    = hit.getHighlightFields().get("fileName").getFragments()[0].string();
//                String fileNameNew = fileName.replaceAll(key,"<span style='color:#F77234'>"+key+"</span>");
//                esFolderFileVo.setFileName(fileNameNew);
//            }
//
//            // 判空处理
//            if(null == esFolderFileVo.getFileName()){
//                // 高亮处理
//                String fileName    = hit.getSourceAsMap().get("fileName").toString();
//                String fileNameNew = fileName.replaceAll(key,"<span style='color:#F77234'>"+key+"</span>");
//                esFolderFileVo.setFileName(fileNameNew);
//            }
//
//            if(null == esFolderFileVo.getFileContent()){
//                String content = hit.getSourceAsMap().get("content").toString();
//                if(content.length()>0){
//                    esFolderFileVo.setFileContent(content.length()>100?content.substring(0,100):content);
//                }else{
//                    esFolderFileVo.setFileContent("");
//                }
//            }
//            list.add(esFolderFileVo);
//        }
//
//        try {
//            rt = objectMapper.writeValueAsString(list);
//
//            // 2. 记录搜索历史
//            asyncAddSearchHistory(key);
//
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
        return rt;
    }


    /**
     * 记录搜索历史（聚合）
     * 1. 根据关键字查询用户搜索历史
     * 2. 逻辑处理
     * */
    @Async
    public void asyncAddSearchHistory(String key){

        // 1. 根据关键字查询用户搜索历史
        LambdaQueryWrapper<SearchHistory> lqw = Wrappers.lambdaQuery();
        LoginUser loginUser = LoginHelper.getLoginUser();

        lqw.eq(StringUtils.isNotBlank(key), SearchHistory::getKeyword, key)
            .eq(true,SearchHistory::getCreateBy,loginUser.getUserId());
        SearchHistoryVo searchHistoryVo = searchHistoryMapper.selectVoOne(lqw);

        // 2. 逻辑处理
        if(null == searchHistoryVo){
            // 插入
            SearchHistory searchHistory = new SearchHistory();
            searchHistory.setKeyword(key);
            searchHistory.setCount(1);
            searchHistoryMapper.insert(searchHistory);
        }else{
            // 更新数量
            SearchHistory searchHistory = MapstructUtils.convert(searchHistoryVo, SearchHistory.class);
            searchHistory.setCount(searchHistory.getCount()+1);
            searchHistoryMapper.updateById(searchHistory);
        }

    }
}
