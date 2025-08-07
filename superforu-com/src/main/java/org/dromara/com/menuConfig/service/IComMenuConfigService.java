package org.dromara.com.menuConfig.service;

import org.dromara.com.menuConfig.domain.ComMenuConfig;
import org.dromara.com.menuConfig.domain.vo.ComMenuConfigVo;
import org.dromara.com.menuConfig.domain.bo.ComMenuConfigBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 菜单配置Service接口
 *
 * @author Lion Li
 * @date 2023-11-28
 */
public interface IComMenuConfigService {

    /**
     * 查询菜单配置
     */
    ComMenuConfigVo queryById(Long id);

    /**
     * 查询菜单配置列表
     */
    TableDataInfo<ComMenuConfigVo> queryPageList(ComMenuConfigBo bo, PageQuery pageQuery);

    /**
     * 查询菜单配置列表
     */
    List<ComMenuConfigVo> queryList(ComMenuConfigBo bo);

    /**
     * 新增菜单配置
     */
    Boolean insertByBo(ComMenuConfigBo bo);

    /**
     * 修改菜单配置
     */
    Boolean updateByBo(ComMenuConfigBo bo);

    /**
     * 校验并批量删除菜单配置信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
