package org.dromara.kb.storageConfig.service;

import org.dromara.kb.storageConfig.domain.StorageConfig;
import org.dromara.kb.storageConfig.domain.vo.StorageConfigVo;
import org.dromara.kb.storageConfig.domain.bo.StorageConfigBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 集成存储配置Service接口
 *
 * @author Lion Li
 * @date 2023-11-28
 */
public interface IStorageConfigService {

    /**
     * 查询集成存储配置
     */
    StorageConfigVo queryById(Long id);

    /**
     * 查询集成存储配置列表
     */
    TableDataInfo<StorageConfigVo> queryPageList(StorageConfigBo bo, PageQuery pageQuery);

    /**
     * 查询集成存储配置列表
     */
    List<StorageConfigVo> queryList(StorageConfigBo bo);

    /**
     * 新增集成存储配置
     */
    Boolean insertByBo(StorageConfigBo bo);

    /**
     * 修改集成存储配置
     */
    Boolean updateByBo(StorageConfigBo bo);

    /**
     * 校验并批量删除集成存储配置信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
