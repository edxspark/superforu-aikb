package org.dromara.kb.fileShare.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.kb.fileShare.domain.bo.FileShareBo;
import org.dromara.kb.fileShare.domain.vo.FileShareVo;
import org.dromara.kb.fileShare.domain.FileShare;
import org.dromara.kb.fileShare.mapper.FileShareMapper;
import org.dromara.kb.fileShare.service.IFileShareService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 文件分享Service业务层处理
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@RequiredArgsConstructor
@Service
public class FileShareServiceImpl implements IFileShareService {

    private final FileShareMapper baseMapper;

    /**
     * 查询文件分享
     */
    @Override
    public FileShareVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询文件分享列表
     */
    @Override
    public TableDataInfo<FileShareVo> queryPageList(FileShareBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FileShare> lqw = buildQueryWrapper(bo);
        Page<FileShareVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询文件分享列表
     */
    @Override
    public List<FileShareVo> queryList(FileShareBo bo) {
        LambdaQueryWrapper<FileShare> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FileShare> buildQueryWrapper(FileShareBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FileShare> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getLinkUserId() != null, FileShare::getLinkUserId, bo.getLinkUserId());
        lqw.eq(bo.getLinkFileId() != null, FileShare::getLinkFileId, bo.getLinkFileId());
        lqw.eq(StringUtils.isNotBlank(bo.getShareCode()), FileShare::getShareCode, bo.getShareCode());
        return lqw;
    }

    /**
     * 新增文件分享
     */
    @Override
    public Boolean insertByBo(FileShareBo bo) {
        FileShare add = MapstructUtils.convert(bo, FileShare.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改文件分享
     */
    @Override
    public Boolean updateByBo(FileShareBo bo) {
        FileShare update = MapstructUtils.convert(bo, FileShare.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FileShare entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除文件分享
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
