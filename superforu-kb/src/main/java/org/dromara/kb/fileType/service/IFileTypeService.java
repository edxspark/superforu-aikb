package org.dromara.kb.fileType.service;

import org.dromara.kb.fileType.domain.FileType;
import org.dromara.kb.fileType.domain.vo.FileTypeVo;
import org.dromara.kb.fileType.domain.bo.FileTypeBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.system.domain.vo.SysDictDataVo;

import java.util.Collection;
import java.util.List;

/**
 * 文件类型Service接口
 *
 * @author Lion Li
 * @date 2023-12-08
 */
public interface IFileTypeService {

    /**
     * 查询文件类型
     */
    FileTypeVo queryById(Long id);

    /**
     * 查询文件类型列表
     */
    TableDataInfo<SysDictDataVo> queryPageList(FileTypeBo bo, PageQuery pageQuery);

    /**
     * 查询文件类型列表
     */
    List<FileTypeVo> queryList(FileTypeBo bo);

    /**
     * 新增文件类型
     */
    Boolean insertByBo(FileTypeBo bo);

    /**
     * 修改文件类型
     */
    Boolean updateByBo(FileTypeBo bo);

    /**
     * 校验并批量删除文件类型信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);


}
