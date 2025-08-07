package org.dromara.kb.recycle.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.dromara.ai.service.IEmbeddingService;
import org.dromara.ai.service.bo.EmbeddingServiceBo;
import org.dromara.ai.service.imp.EmbeddingService;
import org.dromara.com.user.domain.ComUser;
import org.dromara.com.user.domain.vo.ComUserVo;
import org.dromara.com.user.mapper.ComUserMapper;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.kb.es.service.imp.FileESServiceImp;
import org.dromara.kb.folderFile.domain.FolderFile;
import org.dromara.kb.folderFile.mapper.FolderFileMapper;
import org.dromara.kb.km.domain.Km;
import org.dromara.kb.km.mapper.KmMapper;
import org.dromara.system.domain.bo.SysDictDataBo;
import org.dromara.system.domain.vo.SysDictDataVo;
import org.dromara.system.service.ISysDictDataService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.dromara.kb.recycle.domain.bo.RecycleBo;
import org.dromara.kb.recycle.domain.vo.RecycleVo;
import org.dromara.kb.recycle.domain.Recycle;
import org.dromara.kb.recycle.mapper.RecycleMapper;
import org.dromara.kb.recycle.service.IRecycleService;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * 回收站Service业务层处理
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@RequiredArgsConstructor
@Service
public class RecycleServiceImpl implements IRecycleService {

    private final RecycleMapper baseMapper;

    private final ComUserMapper comUserMapper;

    private final FolderFileMapper folderFileMapper;

    private final FileESServiceImp fileESServiceImp;

    private final KmMapper kmMapper;

    private final ISysDictDataService iSysDictDataService;

    private final IEmbeddingService iEmbeddingService;

    /**
     * 查询回收站
     */
    @Override
    public RecycleVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询回收站列表
     * 1. 查询回收站记录
     * 2. 填充修改人
     * 3. 填充颜色图标
     * 4. 填充释放剩余天数(保留30天)和其他
     */
    @Override
    public TableDataInfo<RecycleVo> queryPageList(RecycleBo bo, PageQuery pageQuery) {

        // 1. 查询回收站记录
        LambdaQueryWrapper<Recycle> lqw = buildQueryWrapper(bo);
        Page<RecycleVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        if(result.getTotal()==0){
            return TableDataInfo.build(result);
        }

        // 2. 填充修改人
        // 获取用户信息列表
        List<Long> userIdsBefore = new ArrayList<Long>();
        for(RecycleVo recycleVo:result.getRecords()){
            userIdsBefore.add(Long.parseLong(recycleVo.getUpdateBy()));
        }

        List<Long> userIds = new LinkedList<>(new TreeSet<>(userIdsBefore));
        List<ComUserVo> comUserList = comUserMapper.selectVoList(new LambdaQueryWrapper<ComUser>().in(ComUser::getId, userIds));

        // 填充
        for(RecycleVo recycleVo:result.getRecords()){
            for(ComUserVo comUserVo:comUserList){
                // 修改人
                if(recycleVo.getUpdateBy().equals(""+comUserVo.getId())){
                    recycleVo.setUpdateBy(comUserVo.getUserName());
                    break;
                }
            }
        }

        // 3. 填充颜色图标
        // 获取图标信息
        SysDictDataBo dictDataFileTypeBo = new SysDictDataBo();
        dictDataFileTypeBo.setDictType("file_type");
        List<SysDictDataVo> fileTypeList = iSysDictDataService.selectDictDataList(dictDataFileTypeBo);

        for(RecycleVo recycleVo:result.getRecords()){
            for(SysDictDataVo sysDictDataVo:fileTypeList){

                // 是否是文件夹
                if("folder".equals(recycleVo.getLinkFileTypeCode())){
                    recycleVo.setIsFolder(1L);
                }else{
                    recycleVo.setIsFolder(0L);
                }

                // 填充文件夹和文件图标
                if(recycleVo.getLinkFileTypeCode().equals(sysDictDataVo.getDictValue())){
                    JSONObject jsonObject = JSONUtil.parseObj(sysDictDataVo.getRemark());
                    recycleVo.setFileIcon(jsonObject.getStr("icon"));
                    recycleVo.setFileIconColor(jsonObject.getStr("color"));
                    break;
                }else{
                    // 填充知识库图标
                    if("km".equals(recycleVo.getLinkFileTypeCode())){
                        recycleVo.setFileIcon("icon-apps");
                        recycleVo.setFileIconColor("#722ED1");
                        break;
                    }
                }
            }
        }

        // 4. 填充释放剩余天数和其他
        for(RecycleVo recycleVo:result.getRecords()){
            // 文件名
            recycleVo.setFileName(recycleVo.getLinkName());

            // 4. 填充释放剩余天数(保留30天)和其他
            Date createTime = recycleVo.getCreateTime();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            String createTimeString = sf.format(createTime);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate createDate = LocalDate.parse(createTimeString,formatter);
            LocalDate todayDate = LocalDate.now();

            long daysBetween = ChronoUnit.DAYS.between(createDate, todayDate);
            long remainderDays = 30L-daysBetween;
            recycleVo.setRemainderDays(remainderDays+"");
        }

        return TableDataInfo.build(result);
    }

    /**
     * 查询回收站列表
     */
    @Override
    public List<RecycleVo> queryList(RecycleBo bo) {
        LambdaQueryWrapper<Recycle> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<Recycle> buildQueryWrapper(RecycleBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Recycle> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getType()), Recycle::getType, bo.getType());
        lqw.eq(bo.getLinkId() != null, Recycle::getLinkId, bo.getLinkId());
        lqw.like(StringUtils.isNotBlank(bo.getLinkName()), Recycle::getLinkName, bo.getLinkName());
        return lqw;
    }

    /**
     * 新增回收站
     */
    @Override
    public Boolean insertByBo(RecycleBo bo) {
        Recycle add = MapstructUtils.convert(bo, Recycle.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 删除超过30天的回收站记录
     * 每晚凌晨1:00 开始执行
     * 1. 查询出回收站记录(超过30天)
     * 2. 异常执行删除任务
     * */
    @Override
    public Boolean releaseExpireRecord(){
        boolean rt = true;
        // 1. 查询出回收站记录(超过30天)
        LambdaQueryWrapper<Recycle> lqw = Wrappers.lambdaQuery();
        lqw.lt(Recycle::getCreateTime, LocalDateTime.now().minusDays(30));
        List<RecycleVo> listRecycle = baseMapper.selectVoList(lqw);

        //  2. 异常执行删除任务
        for(RecycleVo recycleVo:listRecycle){
            asyncDelete(recycleVo);
        }
        return rt;
    }

    /**
     * 异常执行删除任务
     * 1. 删除回收站记录
     * 2. 删除图片和视频(markdown)
     * 3. 删除ES记录
     * 4. 删除AI Embedding
     * */
    @Async
    public void asyncDelete(RecycleVo recycleVo){

        // 1. 删除回收站记录
        baseMapper.deleteById(recycleVo.getId());

        if(!"km".equals( recycleVo.getType())){
            FolderFile folderFile = folderFileMapper.selectById(recycleVo.getLinkId());

            // 2. 删除图片和视频(markdown)
            if("markdown".equals(folderFile.getLinkFileTypeCode())){
               // 解析markdown中含有的图片和视频
            }

            // 3. 删除ES记录
            fileESServiceImp.deleteFileESData(folderFile);

            // 4. 删除AI Embedding
            EmbeddingServiceBo embeddingServiceBo = new EmbeddingServiceBo();
            iEmbeddingService.deleteDocumentFromVectorDB(embeddingServiceBo);
        }

    }


    /**
     * 修改回收站
     */
    @Override
    public Boolean updateByBo(RecycleBo bo) {
        Recycle update = MapstructUtils.convert(bo, Recycle.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(Recycle entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除回收站
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }

    /**
     * 恢复（事务）
     * 1. 获取回收记录信息
     * 2. 恢复知识库｜文件夹｜文档
     * 3. 删除回收站记录
     */
    @Override
    public Boolean recover(Long id) {
        boolean rt = false;
        // 1. 获取回收记录信息
        RecycleVo recycleVo = baseMapper.selectVoById(id);

        // 2. 恢复知识库｜文件夹｜文档
        if("file".equals(recycleVo.getType()) || "folder".equals(recycleVo.getType())){
            LambdaQueryWrapper<FolderFile> lqw = Wrappers.lambdaQuery();
            lqw.eq(true, FolderFile::getLinkCycleId,id);
            FolderFile folderFile = new FolderFile();
            folderFile.setDelFlag(0L);
            folderFileMapper.update(folderFile,lqw);

        }else if("km".equals(recycleVo.getType())){
            // 恢复知识库
            Km km = new Km();
            km.setId(id);
            km.setDelFlag(0L);
            kmMapper.updateById(km);


            // 恢复知识库文件夹和文档
            LambdaQueryWrapper<FolderFile> lqw = Wrappers.lambdaQuery();
            lqw.eq(true, FolderFile::getLinkCycleId,id);
            FolderFile folderFile = new FolderFile();
            folderFile.setDelFlag(0L);
            folderFileMapper.update(folderFile,lqw);
        }

        // 3. 删除回收站记录
        rt = baseMapper.deleteById(id)>0;

        return rt;
    }
}
