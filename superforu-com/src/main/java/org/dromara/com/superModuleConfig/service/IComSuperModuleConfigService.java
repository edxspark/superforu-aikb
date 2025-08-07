package org.dromara.com.superModuleConfig.service;

import org.dromara.com.superModuleConfig.domain.ComSuperModuleConfig;
import org.dromara.com.superModuleConfig.domain.bo.ComSuperModuleConfigEditBo;
import org.dromara.com.superModuleConfig.domain.vo.ComSuperModuleConfigVo;
import org.dromara.com.superModuleConfig.domain.bo.ComSuperModuleConfigBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 超级模块配置Service接口
 *
 * @author JackLiao
 * @date 2023-12-11
 */
public interface IComSuperModuleConfigService {

    /**
     * 查询超级模块配置
     */
    ComSuperModuleConfigVo queryById(Long id);

    /**
     * 查询超级模块配置列表
     */
    TableDataInfo<ComSuperModuleConfigVo> queryPageList(ComSuperModuleConfigBo bo, PageQuery pageQuery);


    /**
     * 新增超级模块配置
     */
    Boolean insertByBo(ComSuperModuleConfigBo bo);

    /**
     * 修改超级模块配置
     */
    Boolean updateByBo(ComSuperModuleConfigEditBo bo);

    /**
     * 修改超级模块配置
     */
    Boolean updateByBo(ComSuperModuleConfigBo bo);

    /**
     * 查询已初始化的超级模块配置列表
     */
    String queryInitializationList();

    List<ComSuperModuleConfigVo> queryUserSuperModuleConfigList(List<String> ids);

}
