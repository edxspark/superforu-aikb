package org.dromara.com.quickEntranceConfig.service;

import org.dromara.com.quickEntranceConfig.domain.bo.ComQuickEntranceConfigAddBo;
import org.dromara.com.quickEntranceConfig.domain.bo.ComQuickEntranceConfigEditBo;
import org.dromara.com.quickEntranceConfig.domain.vo.ComQuickEntranceConfigVo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.List;

/**
 * 快捷入口配置Service接口
 *
 * @author JackLiao
 * @date 2023-12-11
 */
public interface IComQuickEntranceConfigService {


    /**
     * 查询快捷入口配置列表
     */
    TableDataInfo<ComQuickEntranceConfigVo> queryPageList(PageQuery pageQuery);

    /**
     * 查询已初始化的快捷入口配置
     */
    String queryInitializationList();

    /**
     * 新增快捷入口配置
     */
    Boolean insertByBo(ComQuickEntranceConfigAddBo bo);

    /**
     * 修改快捷入口配置
     */
    Boolean updateByBo(ComQuickEntranceConfigEditBo bo);

    List<ComQuickEntranceConfigVo> queryUserQuickEntranceConfigList(List<String> ids);

}
