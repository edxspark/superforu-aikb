package org.dromara.kb.fileContent.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.kb.es.service.FileESService;
import org.dromara.kb.fileRecently.domain.FileRecently;
import org.dromara.kb.fileRecently.mapper.FileRecentlyMapper;
import org.dromara.kb.folderFile.domain.FolderFile;
import org.dromara.kb.folderFile.mapper.FolderFileMapper;
import org.dromara.kb.km.domain.Km;
import org.dromara.kb.km.mapper.KmMapper;
import org.dromara.kb.utils.common.FileSizeUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.dromara.kb.fileContent.domain.bo.FileContentBo;
import org.dromara.kb.fileContent.domain.vo.FileContentVo;
import org.dromara.kb.fileContent.domain.FileContent;
import org.dromara.kb.fileContent.mapper.FileContentMapper;
import org.dromara.kb.fileContent.service.IFileContentService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 文件内容Service业务层处理
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@EnableAsync
@RequiredArgsConstructor
@Service
public class FileContentServiceImpl implements IFileContentService {

    private final FileContentMapper baseMapper;

    private final FileRecentlyMapper fileRecentlyMapper;

    private final FolderFileMapper folderFileMapper;

    private final KmMapper kmMapper;

    private final FileESService fileESService;

    /**
     * 查询文件内容
     */
    @Override
    public FileContentVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询文件内容列表
     */
    @Override
    public TableDataInfo<FileContentVo> queryPageList(FileContentBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FileContent> lqw = buildQueryWrapper(bo);
        Page<FileContentVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询文件内容列表
     */
    @Override
    public List<FileContentVo> queryList(FileContentBo bo) {
        LambdaQueryWrapper<FileContent> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FileContent> buildQueryWrapper(FileContentBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FileContent> lqw = Wrappers.lambdaQuery();
        return lqw;
    }

    /**
     * 新增文件内容
     */
    @Override
    public Boolean insertByBo(FileContentBo bo) {
        FileContent add = MapstructUtils.convert(bo, FileContent.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改文件内容
     * 1. 修改文件内容
     * 2. 计算文件大小
     * 3. 统计知识库已用空间大小
     * 4. 记录最近编辑
     * 5. 更新ES内容（文件类型=在线文档）
     */
    @Transactional
    @Override
    public Boolean updateByBo(FileContentBo bo) {
        FileContent update = MapstructUtils.convert(bo, FileContent.class);
        FolderFile folderFile   = folderFileMapper.selectById(bo.getLinkFileId());

        // 文件内容大小
        long newFileContentSize = FileSizeUtil.contextSize(bo.getLinkFileContent());
        long oldFileContentSize = folderFile.getFileSpace();

        // 1. 修改文件内容
        update.setId(folderFile.getLinkFileContentId());
        validEntityBeforeSave(update);
        int updateCount = baseMapper.updateById(update);

        // 2. 修改文件
        folderFile.setAiStatus(1);
        folderFile.setFileSpace(newFileContentSize);
        folderFileMapper.updateById(folderFile);

        // 3. 统计知识库已用空间大小
        long usedSpace = newFileContentSize - oldFileContentSize;
        Km km = kmMapper.selectById(folderFile.getLinkKmId());
        km.setUsedSpace(km.getUsedSpace()+usedSpace);
        kmMapper.updateById(km);

        // 4. 记录最近编辑
        asyncSaveFileRecently(bo,folderFile);

        // 5. 更新ES内容（文件类型=在线文档）
        if("doc".equals(folderFile.getLinkFileTypeCode())){
            asyncUpdateFileContentESData(update);
        }

        return updateCount > 0;
    }

    @Async
    public void asyncUpdateFileContentESData(FileContent update){
        fileESService.updateFileContentESData(update);
    }


    /**
     * 清空最近编辑(异步)
     * 1. 清空历史(条件:文件ID、编辑者)
     * 2. 新增记录
     * */
    @Async
    public void asyncSaveFileRecently(FileContentBo bo,FolderFile folderFile){

        // 1.清空历史(条件:文件ID、编辑者)
        LambdaQueryWrapper<FileRecently> fileRecentlyDelLqw = Wrappers.lambdaQuery();
        fileRecentlyDelLqw.eq(true, FileRecently::getLinkFileId, bo.getLinkFileId());
        fileRecentlyDelLqw.eq(true,FileRecently::getLinkUserId,LoginHelper.getLoginUser().getUserId());
        fileRecentlyMapper.delete(fileRecentlyDelLqw);

        // 新增记录
        FileRecently fileRecently = new FileRecently();
        fileRecently.setLinkFileId(bo.getLinkFileId());
        fileRecently.setLinkUserId(LoginHelper.getLoginUser().getUserId());
        fileRecently.setFileName(folderFile.getFileName());
        fileRecently.setFileType(folderFile.getLinkFileTypeName());
        fileRecentlyMapper.insert(fileRecently);
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FileContent entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除文件内容
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
