package org.dromara.kb.fileType.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.system.domain.SysDictData;
import org.dromara.system.domain.bo.SysDictDataBo;
import org.dromara.system.domain.vo.SysDictDataVo;
import org.dromara.system.service.ISysDictDataService;
import org.dromara.system.service.impl.SysDictDataServiceImpl;
import org.springframework.stereotype.Service;
import org.dromara.kb.fileType.domain.bo.FileTypeBo;
import org.dromara.kb.fileType.domain.vo.FileTypeVo;
import org.dromara.kb.fileType.domain.FileType;
import org.dromara.kb.fileType.mapper.FileTypeMapper;
import org.dromara.kb.fileType.service.IFileTypeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 文件类型Service业务层处理
 *
 * @author Lion Li
 * @date 2023-12-08
 */
@RequiredArgsConstructor
@Service
public class FileTypeServiceImpl implements IFileTypeService {

    private final FileTypeMapper baseMapper;

    private final ISysDictDataService iSysDictDataService;

    /**
     * 查询文件类型
     */
    @Override
    public FileTypeVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询文件类型列表
     * 1. 获取字典配置(key=file_type)
     * 2. 返回处理数据
     */
    @Override
    public TableDataInfo<SysDictDataVo> queryPageList(FileTypeBo bo, PageQuery pageQuery) {
        // 1. 获取字典配置(key=file_type)
        SysDictDataBo dictDataFileTypeBo = new SysDictDataBo();
        dictDataFileTypeBo.setDictType("file_type");
        List<SysDictDataVo> fileTypeList = iSysDictDataService.selectDictDataList(dictDataFileTypeBo);

        return TableDataInfo.build(fileTypeList);
    }

    /**
     * 查询文件类型列表
     */
    @Override
    public List<FileTypeVo> queryList(FileTypeBo bo) {
        LambdaQueryWrapper<FileType> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FileType> buildQueryWrapper(FileTypeBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FileType> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), FileType::getName, bo.getName());
        lqw.like(StringUtils.isNotBlank(bo.getCode()), FileType::getCode, bo.getCode());
        return lqw;
    }

    /**
     * 新增文件类型
     */
    @Override
    public Boolean insertByBo(FileTypeBo bo) {
        FileType add = MapstructUtils.convert(bo, FileType.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改文件类型
     */
    @Override
    public Boolean updateByBo(FileTypeBo bo) {
        FileType update = MapstructUtils.convert(bo, FileType.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FileType entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除文件类型
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }


}
