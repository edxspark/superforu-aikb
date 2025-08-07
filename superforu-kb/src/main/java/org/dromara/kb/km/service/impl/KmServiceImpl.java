package org.dromara.kb.km.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.dromara.ai.service.IEmbeddingService;
import org.dromara.ai.service.bo.EmbeddingServiceBo;
import org.dromara.com.teamMate.domain.ComTeamMate;
import org.dromara.com.teamMate.domain.vo.ComTeamMateVo;
import org.dromara.com.teamMate.mapper.ComTeamMateMapper;
import org.dromara.com.user.domain.ComUser;
import org.dromara.com.user.domain.vo.ComUserVo;
import org.dromara.com.user.mapper.ComUserMapper;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.utils.DateUtils;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.kb.folderFile.domain.FolderFile;
import org.dromara.kb.folderFile.domain.vo.FolderFileVo;
import org.dromara.kb.folderFile.mapper.FolderFileMapper;
import org.dromara.kb.folderFile.service.IFolderFileService;
import org.dromara.kb.km.domain.bo.KmTreeDataBo;
import org.dromara.kb.km.domain.vo.KmTreeDataVo;
import org.dromara.kb.kmCollaboration.domain.KmCollaboration;
import org.dromara.kb.kmCollaboration.domain.vo.KmCollaborationVo;
import org.dromara.kb.kmCollaboration.mapper.KmCollaborationMapper;
import org.dromara.kb.kmShare.domain.KmShare;
import org.dromara.kb.kmShare.mapper.KmShareMapper;
import org.dromara.kb.recycle.domain.bo.RecycleBo;
import org.dromara.kb.recycle.service.IRecycleService;
import org.dromara.kb.utils.common.FileSizeUtil;
import org.dromara.system.domain.SysOss;
import org.dromara.system.domain.vo.SysOssVo;
import org.dromara.system.mapper.SysOssMapper;
import org.dromara.system.service.ISysConfigService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.dromara.kb.km.domain.bo.KmBo;
import org.dromara.kb.km.domain.vo.KmVo;
import org.dromara.kb.km.domain.Km;
import org.dromara.kb.km.mapper.KmMapper;
import org.dromara.kb.km.service.IKmService;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 知识库Service业务层处理
 *
 * @author zzg
 * @date 2023-12-07
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class KmServiceImpl implements IKmService {

    private final KmMapper baseMapper;
    private final KmShareMapper shareMapper;
    private final KmCollaborationMapper collaborationMapper;
    private final ComTeamMateMapper comTeamMateMapper;
    private final IRecycleService recycleService;
    private final IFolderFileService iFolderFileService;
    private final FolderFileMapper folderFileMapper;
    private final SysOssMapper sysOssMapper;
    private final IEmbeddingService iEmbeddingService;
    private final ComUserMapper comUserMapper;


    /**
     * AI向量化(批量)
     * 1. 检索开启AI的知识库列表
     * 2. 异步执行知识库向量化
     * */
    @Override
    public Boolean kmEmbedding(){
        log.info("#####AI向量化批量--START");
        // 1. 检索开启AI的知识库列表
        LambdaQueryWrapper<Km> lqw = Wrappers.lambdaQuery();
        lqw.eq(Km::getIsAiOpen, 1);
        List<KmVo> listKM = baseMapper.selectVoList(lqw);
        log.info("#####开启AI知识库数量:"+listKM.size());

        // 2. 异步执行知识库向量化
        for(KmVo kmVo:listKM){
            asyncEmbeddingFiles(kmVo);
        }
        log.info("#####AI向量化批量--END");
        return true;
    }


    /**
     * AI向量化(单个)
     * 1.更新文件状态
     * */
    @Override
    public Boolean kmEmbeddingSingleFile(Long fileId){
        log.info("#####AI向量化单个--START");
        // 1. 更新文件状态
        FolderFile folderFile = new FolderFile();
        folderFile.setId(fileId);
        folderFile.setAiStatus(1);
        log.info("#####AI向量化单个--END");
        return folderFileMapper.updateById(folderFile)>0;
    }



    /**
     * 异步文档向量化
     * 1. 查询出需要向量华的文档(距离上一次向量化>=5分钟 && 文件类型=pdf、markdown)
     * 2. 调用文档ai embedding接口
     * 3. 更新文档AI状态和AI同步时间
     * */
    public void asyncEmbeddingFiles(KmVo kmVo){

        log.info("#####AI向量化KM-NAME:"+kmVo.getName());
        // 参数设置
        Date now = DateUtils.getNowDate();
        Date addNowDate = DateUtils.addSeconds(now,5*3600);

        // 1. 查询出需要向量化的文档(距离上一次向量化>=5分钟 && 文件类型=pdf、markdown)
        LambdaQueryWrapper<FolderFile> lqwFolderFile = Wrappers.lambdaQuery();
        lqwFolderFile.eq(FolderFile::getLinkKmId, kmVo.getId());
        lqwFolderFile.eq(FolderFile::getAiStatus, 1);       // 1: 待同步
        lqwFolderFile.eq(FolderFile::getStatus,0);          // 0: 文档使用中
        //lqwFolderFile.lt(FolderFile::getAiSyncTime,addNowDate); // 小于5个小时内
        ArrayList<String> fileExtensionList = new ArrayList<String>();
        fileExtensionList.add("markdown");
        fileExtensionList.add("pdf");
        lqwFolderFile.in(FolderFile::getFileExtension,fileExtensionList); // 需要文件类型
        lqwFolderFile.orderByAsc(FolderFile::getUpdateTime); // 先进先处理

        String agentType = kmVo.getAgentType();
        String limitCount = "3";
        if("COM".equals(agentType)){
            limitCount = "10";
        }

        lqwFolderFile.last("limit "+limitCount); // 每次获取x条进行处理
        List<FolderFileVo> listFolderFileVo = folderFileMapper.selectVoList(lqwFolderFile);

        if(listFolderFileVo.isEmpty()){
            log.info("#####无需同步文件，KM_ID:"+kmVo.getId());
            return;
        }

        // 同步锁定状态(避免重复执行)
        log.info("#####锁定同步文件:"+listFolderFileVo.size());
        List<FolderFile> lockFolderFileList = new ArrayList<>();
        for(FolderFileVo folderFileVo:listFolderFileVo){
            FolderFile folderFile = new FolderFile();
            folderFile.setId(folderFileVo.getId());
            // 2: 同步中
            folderFile.setAiStatus(2);
            lockFolderFileList.add(folderFile);
        }
        folderFileMapper.updateBatchById(lockFolderFileList);

        log.info("#####AI向量化文件数量:"+listFolderFileVo.size());
        for(FolderFileVo folderFileVo:listFolderFileVo){
            fileEmbedding(kmVo, folderFileVo);
        }
    }

    @Async
    public void fileEmbedding(KmVo kmVo, FolderFileVo folderFileVo){
        EmbeddingServiceBo embeddingServiceBo = new EmbeddingServiceBo();
        embeddingServiceBo.setFileId(""+folderFileVo.getId());
        embeddingServiceBo.setFileName(folderFileVo.getFileName());
        embeddingServiceBo.setFileType(folderFileVo.getFileExtension());
        embeddingServiceBo.setKmId(""+folderFileVo.getLinkKmId());
        embeddingServiceBo.setAgentType(kmVo.getAgentType());
        embeddingServiceBo.setFileContent("");

        // 获取pdf URL
        if(folderFileVo.getFileExtension().equals("pdf")){
            SysOssVo sysOssVo = sysOssMapper.selectVoById(folderFileVo.getPicUrl());
            embeddingServiceBo.setFileURL(sysOssVo.getUrl());
        }

        // 2. 调用文档ai embedding接口
        boolean embeddingResult = iEmbeddingService.documentToVectorDB(embeddingServiceBo);
        log.info("#####向量化接口同步文档："+ folderFileVo.getId()+":"+folderFileVo.getFileName());
        log.info("#####向量化接口同步结果："+ embeddingResult);

        // 3. 更新文档AI状态和AI同步时间
        if(embeddingResult){
            FolderFile folderFile = new FolderFile();
            folderFile.setId(folderFileVo.getId());
            // 3: 已完成
            folderFile.setAiStatus(3);
            folderFile.setAiSyncTime(DateUtils.getNowDate());
            folderFileMapper.updateById(folderFile);
        }
    }



    /**
     * 查询知识库
     */
    @Override
    public KmVo queryById(Long id){
        KmVo kmVo = baseMapper.selectVoById(id);

        // 封面完整路径设置
        if(null != kmVo){
            String fileServer = SpringUtils.getBean(ISysConfigService.class).selectConfigByKey("com.file.server.url");
            kmVo.setPicUrl(fileServer+kmVo.getPicUrl());
        }

        return kmVo;
    }

    /**
     * 查询我的知识库和分享给我的知识库
     * 1. 我的知识库
     * 2. 分享给我的知识库
     * @param bo 知识库检索对象
     * @author moks.mo
     */
    @Override
    public TableDataInfo<KmVo> queryPageList(KmBo bo,PageQuery pageQuery) {

        // 获取登录用户
        LoginUser loginUser = LoginHelper.getLoginUser();

        // 1. 我的知识库
        bo.setLinkUserAccount(loginUser.getUsername());
        LambdaQueryWrapper<Km> lqw = buildQueryWrapper(bo);
        List<KmVo> kmVoList = baseMapper.selectVoList(lqw);
        for(KmVo kmVo:kmVoList){
            kmVo.setOwnerType("myself");
            kmVo.setPicUrl(kmVo.getPicUrl());
            String maxSpace = FileSizeUtil.bytesToGb(Long.parseLong(kmVo.getMaxSpace()),0).toString();
            kmVo.setMaxSpace(maxSpace);
            String usedSpace = FileSizeUtil.bytesToGb(Long.parseLong(kmVo.getUsedSpace())).toString();
            kmVo.setUsedSpace(usedSpace);
            kmVo.setPercent(FileSizeUtil.percent(usedSpace,maxSpace).toString());
        }

        // 2. 分享给我的知识库
        // 查询出所在团队知识库列表
        List<ComTeamMateVo> comTeamMateVoList = comTeamMateMapper.selectVoList(new LambdaQueryWrapper<ComTeamMate>()
            .eq(ComTeamMate::getLinkUserId,loginUser.getUserId()));
        List<Long> teamIdsList = new ArrayList<Long>();
        if(comTeamMateVoList.size()>0){
            for (ComTeamMateVo teamMateVo:comTeamMateVoList){
                teamIdsList.add(teamMateVo.getLinkTeamId());
            }

            // 查询出协同管理的知识库IDS
            if(teamIdsList.size()>0){
                List<KmCollaborationVo> collaborationVos =  collaborationMapper.selectVoList(new LambdaQueryWrapper<KmCollaboration>()
                    .in(KmCollaboration::getLinkTeamId,teamIdsList));
                List<Long> kmIdsList = new ArrayList<Long>();
                if(collaborationVos.size()>0){
                    for(KmCollaborationVo collaborationVo:collaborationVos){
                        kmIdsList.add(collaborationVo.getLinkKmId());
                    }

                    // 查询出知识库列表
                    List<KmVo> kmVoListShare =  baseMapper.selectVoList(new LambdaQueryWrapper<Km>()
                        .in(Km::getId,kmIdsList));
                    for(KmVo kmVo:kmVoListShare){
                        kmVo.setOwnerType("share");
                        kmVo.setPicUrl(kmVo.getPicUrl());
                        String maxSpace = FileSizeUtil.bytesToGb(Long.parseLong(kmVo.getMaxSpace()),0).toString();
                        kmVo.setMaxSpace(maxSpace);
                        String usedSpace = FileSizeUtil.bytesToGb(Long.parseLong(kmVo.getUsedSpace())).toString();
                        kmVo.setUsedSpace(usedSpace);
                        kmVo.setPercent(FileSizeUtil.percent(usedSpace,maxSpace).toString());
                    }
                    kmVoList.addAll(kmVoListShare);
                }
            }
        }

        // 填充封面
        List<Long> ossIdsBefore = new ArrayList<Long>();
        for(KmVo vo:kmVoList){
            ossIdsBefore.add(Long.parseLong(StringUtils.isEmpty(vo.getPicUrl())?"0":vo.getPicUrl()));
        }

        List<Long> ossIds = new LinkedList<>(new TreeSet<>(ossIdsBefore));
        if(ossIds.size()>0){
            List<SysOssVo> ossList = sysOssMapper.selectVoList(new LambdaQueryWrapper<SysOss>().in(SysOss::getOssId, ossIds));
            for(KmVo kmVo:kmVoList){
                for(SysOssVo sysOssVo:ossList){
                    if(kmVo.getPicUrl().equals(""+sysOssVo.getOssId())){
                        kmVo.setPicOSSUrl(sysOssVo.getUrl());
                        break;
                    }
                }
            }
        }

        return TableDataInfo.build(kmVoList);
    }


    /**
     * 获取主知识库ID
     * */
    @Override
    public String getMainKmId() {
        String mainKmId = "";
        Long userId = LoginHelper.getLoginUser().getUserId();
        List<KmVo> kmVoList = baseMapper.selectVoList(new LambdaQueryWrapper<Km>().eq(Km::getCreateBy, userId));
        if(kmVoList.size()>0){
            mainKmId = ""+ kmVoList.get(0).getId();
        }

        return mainKmId;
    }

    /**
     * 获取开启AI的知识库列表
     * */
    @Override
    public List<KmVo> listIsOpenAI(){
        LoginUser loginUser = LoginHelper.getLoginUser();
        String userName = loginUser.getUsername();
        LambdaQueryWrapper<Km> lqw = Wrappers.lambdaQuery();
        lqw.eq(Km::getLinkUserAccount,userName);
        lqw.eq(Km::getIsAiOpen,1);
        return baseMapper.selectVoList(lqw);
    }


    /**
     * 查询知识库列表
     * @param bo 知识库检索对象
     * @author zzg
     */
    @Override
    public List<KmVo> queryList(KmBo bo) {
        //获取登录用户
        LoginUser loginUser = LoginHelper.getLoginUser();
        bo.setLinkUserAccount(loginUser.getUsername());
        LambdaQueryWrapper<Km> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }



    private LambdaQueryWrapper<Km> buildQueryWrapper(KmBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Km> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getLinkUserAccount()), Km::getLinkUserAccount, bo.getLinkUserAccount());
        lqw.eq(Km::getStatus, 0);
        return lqw;
    }

    /**
     * 新增知识库
     */
    @Override
    public R<KmVo> insertByBo(KmBo bo) {
        Km add = MapstructUtils.convert(bo, Km.class);
        validEntityBeforeSave(add);

        // 设置操作用户
        add.setLinkUserAccount(LoginHelper.getLoginUser().getUsername());

        // 设置默认空间
        add.setMaxSpace(FileSizeUtil.gbToB(Long.parseLong(SpringUtils.getBean(ISysConfigService.class).selectConfigByKey("kb.default.space"))));
        add.setUsedSpace(0L);

        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }

        // 返回值
        KmVo kmVo = new KmVo();
        kmVo.setId(add.getId());
        kmVo.setName(add.getName());
        kmVo.setMark(add.getMark());
        kmVo.setType(add.getType());
        kmVo.setLinkUserAccount(add.getLinkUserAccount());

        // 设置默认空间(GB)
        kmVo.setMaxSpace(FileSizeUtil.bToString(add.getMaxSpace()));
        kmVo.setUsedSpace("0B");
        kmVo.setPicUrl(add.getPicUrl());

        return R.ok(kmVo);
    }

    /**
     * 修改知识库
     * 1. 修改知识库信息
     * 2. 修改AI状态（AI状态发生变化）
     */
    @Override
    public Boolean updateByBo(KmBo bo) {
        Km update = MapstructUtils.convert(bo, Km.class);
        validEntityBeforeSave(update);

        // 获取KM信息
        Km kmOriginal = baseMapper.selectById(bo.getId());
        int isOpenOriginal = kmOriginal.getIsAiOpen();

        // 1. 修改知识库信息
        boolean rt = baseMapper.updateById(update) > 0;

        // 2. 修改AI状态（AI状态发生变化）
        if(isOpenOriginal!=bo.getIsAiOpen()){
            int updateCount = folderFileMapper.updateFolderFileAiStatus(bo.getIsAiOpen(),bo.getId());
            log.info("#####知识库AI状态发生变化，更新文件数量:"+updateCount);
        }

        return rt;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Km entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除知识库
     * 1.删除文档
     * 2.删除协同
     * 3.删除分享
     * 4.删除知识库，记录回收站ID
     * @param ids  知识库集合ID
     * @param isValid  校验标识
     *
     * @author zzg
     */
    @Transactional
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {

        List<Long> idList = (List<Long>)ids;
        long id = idList.get(0);
        KmVo kmVo = baseMapper.selectVoById(id);

        // 1.1 新增回收站记录
        RecycleBo recycleBo = new RecycleBo();
        recycleBo.setType("km");
        recycleBo.setLinkName(kmVo.getName());
        recycleBo.setLinkId(kmVo.getId());
        recycleBo.setLinkFileTypeCode("km");
        recycleBo.setLinkFileTypeName("知识库");
        recycleBo.setLinkKmId(id);
        recycleService.insertByBo(recycleBo);

        // 1.删除文档
        // 查询出当前知识库根目录所有文件或文件夹
        List<FolderFileVo> folderFileVos = folderFileMapper.selectVoList(new LambdaQueryWrapper<FolderFile>()
            .eq(FolderFile::getLinkKmId,id)
            .eq(FolderFile::getParentId,0));
        List<Long> folderFileIds = new ArrayList<>();
        for(FolderFileVo folderFileVo:folderFileVos){
            folderFileIds.add(folderFileVo.getId());
        }

        // 调用文件删除服务
        if(folderFileIds.size()>0){
            iFolderFileService.deleteWithValidByIds(folderFileIds,false,recycleBo.getId());
        }

        // 2.删除协同管理
        LambdaQueryWrapper<KmCollaboration> collaborationLqw = Wrappers.lambdaQuery();
        collaborationLqw.in(KmCollaboration::getLinkKmId,ids);
        KmCollaboration updateKmCollaboration = new KmCollaboration();
        updateKmCollaboration.setStatus(1);
        updateKmCollaboration.setLinkCycleId(recycleBo.getId());
        collaborationMapper.update(updateKmCollaboration,collaborationLqw);

        // 3.删除分享
        LambdaQueryWrapper<KmShare> shareLqw = Wrappers.lambdaQuery();
        shareLqw.in(KmShare::getLinkKmId,ids);
        KmShare updateKmShare = new KmShare();
        updateKmShare.setStatus(1);
        updateKmShare.setLinkCycleId(recycleBo.getId());
        shareMapper.update(updateKmShare,shareLqw);

        // 4.删除知识库，记录回收站ID
        Km km = new Km();
        km.setId(id);
        km.setDelFlag(2L);
        km.setLinkCycleId(recycleBo.getId());
        baseMapper.updateById(km);

        boolean result = false;
        if(baseMapper.deleteById(id) >0){
            result = true;
        }
        return result;
    }

    /**
     * 查询知识库目录树
     * @author moks.mo
     * */
    @Override
    public R<KmTreeDataVo> queryTreeData(KmTreeDataBo bo){
        KmVo kmVo = baseMapper.selectVoById(bo.getId());
        if(null != kmVo){
            KmTreeDataVo kmTreeDataVo = new KmTreeDataVo();
            kmTreeDataVo.setId(kmVo.getId());
            kmTreeDataVo.setType(kmVo.getType());
            kmTreeDataVo.setName(kmVo.getName());
            kmTreeDataVo.setFileKmTreeData(kmVo.getFileKmTreeData());
            return R.ok(kmTreeDataVo);
        }else{
            return R.fail(null);
        }
    }

    /**
     * 更新目录树
     * @author moks.mo
     * */
    @Override
    public R<Void> updateTreeData(KmTreeDataBo bo){
        Km km = new Km();
        km.setId(Long.parseLong(bo.getId()));
        km.setFileKmTreeData(bo.getFileKmTreeData());
        baseMapper.updateById(km);
        return R.ok(km.getFileKmTreeData());
    }


}
