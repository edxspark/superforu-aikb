package org.dromara.kb.fileTemplateType.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.kb.fileTemplateType.domain.bo.FileTemplateTypeBo;
import org.dromara.kb.fileTemplateType.domain.vo.FileTemplateTypeVo;
import org.dromara.kb.fileTemplateType.domain.FileTemplateType;
import org.dromara.kb.fileTemplateType.mapper.FileTemplateTypeMapper;
import org.dromara.kb.fileTemplateType.service.IFileTemplateTypeService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 文档模板类型Service业务层处理
 *
 * @author Lion Li
 * @date 2023-12-12
 */
@RequiredArgsConstructor
@Service
public class FileTemplateTypeServiceImpl implements IFileTemplateTypeService {

    private final FileTemplateTypeMapper baseMapper;

    /**
     * 查询文档模板类型
     */
    @Override
    public FileTemplateTypeVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询文档模板类型列表
     */
    @Override
    public TableDataInfo<FileTemplateTypeVo> queryPageList(FileTemplateTypeBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FileTemplateType> lqw = buildQueryWrapper(bo);
        lqw.orderByAsc(FileTemplateType::getSort);
        Page<FileTemplateTypeVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询文档模板类型列表
     */
    @Override
    public List<FileTemplateTypeVo> queryList(FileTemplateTypeBo bo) {
        LambdaQueryWrapper<FileTemplateType> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FileTemplateType> buildQueryWrapper(FileTemplateTypeBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FileTemplateType> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), FileTemplateType::getName, bo.getName());
        return lqw;
    }

    /**
     * 新增文档模板类型
     */
    @Override
    public Boolean insertByBo(FileTemplateTypeBo bo) {
        FileTemplateType add = MapstructUtils.convert(bo, FileTemplateType.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改文档模板类型
     */
    @Override
    public Boolean updateByBo(FileTemplateTypeBo bo) {
        FileTemplateType update = MapstructUtils.convert(bo, FileTemplateType.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FileTemplateType entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除文档模板类型
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
