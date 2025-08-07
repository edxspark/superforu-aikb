package org.dromara.kb.fileContent.service;

import org.dromara.kb.fileContent.domain.FileContent;
import org.dromara.kb.fileContent.domain.vo.FileContentVo;
import org.dromara.kb.fileContent.domain.bo.FileContentBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 文件内容Service接口
 *
 * @author Lion Li
 * @date 2023-11-28
 */
public interface IFileContentService {

    /**
     * 查询文件内容
     */
    FileContentVo queryById(Long id);

    /**
     * 查询文件内容列表
     */
    TableDataInfo<FileContentVo> queryPageList(FileContentBo bo, PageQuery pageQuery);

    /**
     * 查询文件内容列表
     */
    List<FileContentVo> queryList(FileContentBo bo);

    /**
     * 新增文件内容
     */
    Boolean insertByBo(FileContentBo bo);

    /**
     * 修改文件内容
     */
    Boolean updateByBo(FileContentBo bo);

    /**
     * 校验并批量删除文件内容信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
