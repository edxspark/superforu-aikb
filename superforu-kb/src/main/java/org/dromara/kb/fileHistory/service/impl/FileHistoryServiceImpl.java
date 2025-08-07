package org.dromara.kb.fileHistory.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.kb.fileHistory.domain.bo.FileHistoryBo;
import org.dromara.kb.fileHistory.domain.vo.FileHistoryVo;
import org.dromara.kb.fileHistory.domain.FileHistory;
import org.dromara.kb.fileHistory.mapper.FileHistoryMapper;
import org.dromara.kb.fileHistory.service.IFileHistoryService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 文件历史Service业务层处理
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@RequiredArgsConstructor
@Service
public class FileHistoryServiceImpl implements IFileHistoryService {

    private final FileHistoryMapper baseMapper;

    /**
     * 查询文件历史
     */
    @Override
    public FileHistoryVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询文件历史列表
     */
    @Override
    public TableDataInfo<FileHistoryVo> queryPageList(FileHistoryBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FileHistory> lqw = buildQueryWrapper(bo);
        Page<FileHistoryVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询文件历史列表
     */
    @Override
    public List<FileHistoryVo> queryList(FileHistoryBo bo) {
        LambdaQueryWrapper<FileHistory> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FileHistory> buildQueryWrapper(FileHistoryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FileHistory> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getLinkUserId() != null, FileHistory::getLinkUserId, bo.getLinkUserId());
        lqw.like(StringUtils.isNotBlank(bo.getLinkUserName()), FileHistory::getLinkUserName, bo.getLinkUserName());
        return lqw;
    }

    /**
     * 新增文件历史
     */
    @Override
    public Boolean insertByBo(FileHistoryBo bo) {
        FileHistory add = MapstructUtils.convert(bo, FileHistory.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改文件历史
     */
    @Override
    public Boolean updateByBo(FileHistoryBo bo) {
        FileHistory update = MapstructUtils.convert(bo, FileHistory.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FileHistory entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除文件历史
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
