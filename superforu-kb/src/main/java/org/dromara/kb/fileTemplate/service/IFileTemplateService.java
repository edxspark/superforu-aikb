package org.dromara.kb.fileTemplate.service;

import org.dromara.kb.fileTemplate.domain.FileTemplate;
import org.dromara.kb.fileTemplate.domain.vo.FileTemplateVo;
import org.dromara.kb.fileTemplate.domain.bo.FileTemplateBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 文档模板Service接口
 *
 * @author Lion Li
 * @date 2023-11-28
 */
public interface IFileTemplateService {

    /**
     * 查询文档模板
     */
    FileTemplateVo queryById(Long id);

    /**
     * 查询文档模板列表
     */
    TableDataInfo<FileTemplateVo> queryPageList(FileTemplateBo bo, PageQuery pageQuery);

    /**
     * 查询文档模板列表
     */
    List<FileTemplateVo> queryList(FileTemplateBo bo);

    /**
     * 新增文档模板
     */
    Boolean insertByBo(FileTemplateBo bo);

    /**
     * 修改文档模板
     */
    Boolean updateByBo(FileTemplateBo bo);

    /**
     * 校验并批量删除文档模板信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
