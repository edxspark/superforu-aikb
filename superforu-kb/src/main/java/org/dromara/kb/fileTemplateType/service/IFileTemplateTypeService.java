package org.dromara.kb.fileTemplateType.service;

import org.dromara.kb.fileTemplateType.domain.FileTemplateType;
import org.dromara.kb.fileTemplateType.domain.vo.FileTemplateTypeVo;
import org.dromara.kb.fileTemplateType.domain.bo.FileTemplateTypeBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 文档模板类型Service接口
 *
 * @author Lion Li
 * @date 2023-12-12
 */
public interface IFileTemplateTypeService {

    /**
     * 查询文档模板类型
     */
    FileTemplateTypeVo queryById(Long id);

    /**
     * 查询文档模板类型列表
     */
    TableDataInfo<FileTemplateTypeVo> queryPageList(FileTemplateTypeBo bo, PageQuery pageQuery);

    /**
     * 查询文档模板类型列表
     */
    List<FileTemplateTypeVo> queryList(FileTemplateTypeBo bo);

    /**
     * 新增文档模板类型
     */
    Boolean insertByBo(FileTemplateTypeBo bo);

    /**
     * 修改文档模板类型
     */
    Boolean updateByBo(FileTemplateTypeBo bo);

    /**
     * 校验并批量删除文档模板类型信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
