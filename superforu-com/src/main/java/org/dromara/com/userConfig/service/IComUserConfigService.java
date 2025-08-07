package org.dromara.com.userConfig.service;

import org.dromara.com.quickEntranceConfig.domain.vo.ComQuickEntranceConfigExistVo;
import org.dromara.com.superModuleConfig.domain.vo.ComSuperModuleConfigExistVo;
import org.dromara.com.userConfig.domain.bo.ComUserConfigUpdateBo;
import org.dromara.com.userConfig.domain.vo.ComUserConfigQueryVo;

import java.util.List;

/**
 * 用户配置Service接口
 *
 * @author Lion Li
 * @date 2023-11-28
 */
public interface IComUserConfigService {


    /**
     * 查询用户配置列表
     */
    ComUserConfigQueryVo queryList();

    /**
     * 新增用户配置
     */
    Boolean insertByBo(Long userId);




    List<ComQuickEntranceConfigExistVo> queryQuickEntranceConfigExistList();


    List<ComSuperModuleConfigExistVo> querySuperModuleConfigExistList();


    void addUserConfigByBo(ComUserConfigUpdateBo bo);


    void removeUserConfigByBo(ComUserConfigUpdateBo bo);

}
