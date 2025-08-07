package org.dromara.com.userConfig.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.dromara.com.quickEntranceConfig.domain.vo.ComQuickEntranceConfigExistVo;
import org.dromara.com.quickEntranceConfig.domain.vo.ComQuickEntranceConfigVo;
import org.dromara.com.quickEntranceConfig.service.IComQuickEntranceConfigService;
import org.dromara.com.superModuleConfig.domain.vo.ComSuperModuleConfigExistVo;
import org.dromara.com.superModuleConfig.domain.vo.ComSuperModuleConfigVo;
import org.dromara.com.superModuleConfig.service.IComSuperModuleConfigService;
import org.dromara.com.userConfig.domain.bo.ComUserConfigUpdateBo;
import org.dromara.com.userConfig.domain.vo.ComUserConfigQueryVo;
import org.dromara.com.userConfig.domain.vo.ComUserQuickEntranceConfigVo;
import org.dromara.com.userConfig.domain.vo.ComUserSuperModuleConfigVo;
import org.dromara.com.utils.common.ErrorMsg;
import org.dromara.com.utils.enums.ConfigTypeEnum;
import org.dromara.com.utils.loginUtil.LoginUtil;
import org.dromara.common.core.exception.base.BaseException;
import org.dromara.common.core.utils.MapstructUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.com.userConfig.domain.vo.ComUserConfigVo;
import org.dromara.com.userConfig.domain.ComUserConfig;
import org.dromara.com.userConfig.mapper.ComUserConfigMapper;
import org.dromara.com.userConfig.service.IComUserConfigService;

import java.util.*;

/**
 * 用户配置Service业务层处理
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@RequiredArgsConstructor
@Service
public class ComUserConfigServiceImpl implements IComUserConfigService {


    private final IComQuickEntranceConfigService comQuickEntranceConfigService;

    private final IComSuperModuleConfigService comSuperModuleConfigService;

    private final ComUserConfigMapper baseMapper;

    // 将 Map 定义为一个静态字段，表示全局的配置类型映射
    private static final Map<Integer, String> configTypeMap = new HashMap<>();

    static {
        // 在静态块中初始化 Map，将枚举值映射到 Map 中
        for (ConfigTypeEnum type : ConfigTypeEnum.values()) {
            configTypeMap.put(type.getValue(), type.getDescription());
        }
    }


    /**
     * 查询用户配置列表
     */
    @Override
    public ComUserConfigQueryVo queryList() {
        Long userId = LoginUtil.getUserId();
        List<ComUserConfigVo> comUserConfigVos = baseMapper.selectVoList(new LambdaQueryWrapper<ComUserConfig>().eq(ComUserConfig::getLinkUserId, userId));
        ComUserConfigQueryVo comUserConfigQueryVo = new ComUserConfigQueryVo();
        comUserConfigVos.forEach(comUserConfigVo -> {
            //超级模块配置
            if (ConfigTypeEnum.SUPER_MODULE.getValue().equals(comUserConfigVo.getType())) {
                String configValues = comUserConfigVo.getConfigValues();
                ComUserSuperModuleConfigVo comUserSuperModuleConfigVo = new ComUserSuperModuleConfigVo();
                comUserSuperModuleConfigVo.setType(comUserConfigVo.getType());
                comUserSuperModuleConfigVo.setId(comUserConfigVo.getId());
                comUserSuperModuleConfigVo.setUserSuperModuleConfigList(new ArrayList<>());
                if (ObjectUtil.isNotEmpty(configValues)) {
                    List<String> list = StrUtil.splitTrim(configValues, ',');
                    List<ComSuperModuleConfigVo> comSuperModuleConfigVos = comSuperModuleConfigService.queryUserSuperModuleConfigList(list);
                    comUserSuperModuleConfigVo.setUserSuperModuleConfigList(comSuperModuleConfigVos);
                }
                comUserConfigQueryVo.setComUserSuperModuleConfigVo(comUserSuperModuleConfigVo);

            } else if (ConfigTypeEnum.QUICK_ENTRANCE.getValue().equals(comUserConfigVo.getType())) {
                //快捷入口配置
                String configValues = comUserConfigVo.getConfigValues();
                ComUserQuickEntranceConfigVo comUserQuickEntranceConfigVo = new ComUserQuickEntranceConfigVo();
                comUserQuickEntranceConfigVo.setType(comUserConfigVo.getType());
                comUserQuickEntranceConfigVo.setId(comUserConfigVo.getId());
                comUserQuickEntranceConfigVo.setUserQuickEntranceList(new ArrayList<>());
                if (ObjectUtil.isNotEmpty(configValues)) {
                    List<String> list = StrUtil.splitTrim(configValues, ',');
                    List<ComQuickEntranceConfigVo> comQuickEntranceConfigVos = comQuickEntranceConfigService.queryUserQuickEntranceConfigList(list);
                    comUserQuickEntranceConfigVo.setUserQuickEntranceList(comQuickEntranceConfigVos);
                }
                comUserConfigQueryVo.setComUserQuickEntranceConfigVo(comUserQuickEntranceConfigVo);

            }
        });

        return comUserConfigQueryVo;
    }

    /**
     * 批量新增用户配置
     */
    @Override
    public Boolean insertByBo(Long userId) {

        String quickEntranceConfigIds = comQuickEntranceConfigService.queryInitializationList();

        String superModuleConfigIds = comSuperModuleConfigService.queryInitializationList();

        List<ComUserConfig> comUserConfigs = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : configTypeMap.entrySet()) {
            ComUserConfig add = new ComUserConfig();
            if (ConfigTypeEnum.QUICK_ENTRANCE.getValue().equals(entry.getKey())) {
                add.setConfigValues(quickEntranceConfigIds);
            } else if (ConfigTypeEnum.SUPER_MODULE.getValue().equals(entry.getKey())) {
                add.setConfigValues(superModuleConfigIds);
            } else {
                add.setConfigValues("");
            }
            add.setType(entry.getKey());
            add.setLinkUserId(userId);
            comUserConfigs.add(add);
        }
        return baseMapper.insertBatch(comUserConfigs);
    }


    @Override
    public List<ComQuickEntranceConfigExistVo> queryQuickEntranceConfigExistList() {
        List<ComQuickEntranceConfigVo> comQuickEntranceConfigVos = comQuickEntranceConfigService.queryUserQuickEntranceConfigList(null);
        List<ComQuickEntranceConfigExistVo> convert = new ArrayList<>();
        ComUserConfigVo comUserConfigVo = baseMapper.selectVoOne(new LambdaQueryWrapper<ComUserConfig>()
            .eq(ComUserConfig::getLinkUserId, LoginUtil.getUserId())
            .eq(ComUserConfig::getType, ConfigTypeEnum.QUICK_ENTRANCE.getValue()));
        String configValues = comUserConfigVo.getConfigValues();
        List<String> list = StrUtil.splitTrim(configValues, ',');
        comQuickEntranceConfigVos.forEach(vo -> {
            ComQuickEntranceConfigExistVo configExistVo = new ComQuickEntranceConfigExistVo();
            configExistVo.setCode(vo.getCode());
            configExistVo.setIcon(vo.getIcon());
            configExistVo.setColor(vo.getColor());
            configExistVo.setName(vo.getName());
            configExistVo.setSort(vo.getSort());
            configExistVo.setAttrType(vo.getAttrType());
            configExistVo.setId(vo.getId());
            if (list.contains(vo.getId().toString())) {
                configExistVo.setExistStatus(0);
            } else {
                configExistVo.setExistStatus(1);
            }
            convert.add(configExistVo);

        });

        return convert;

    }

    @Override
    public List<ComSuperModuleConfigExistVo> querySuperModuleConfigExistList() {
        List<ComSuperModuleConfigVo> comSuperModuleConfigVos = comSuperModuleConfigService.queryUserSuperModuleConfigList(null);
        List<ComSuperModuleConfigExistVo> convert = new ArrayList<>();
        ComUserConfigVo comUserConfigVo = baseMapper.selectVoOne(new LambdaQueryWrapper<ComUserConfig>()
            .eq(ComUserConfig::getLinkUserId, LoginUtil.getUserId())
            .eq(ComUserConfig::getType, ConfigTypeEnum.SUPER_MODULE.getValue()));
        String configValues = comUserConfigVo.getConfigValues();
        List<String> list = StrUtil.splitTrim(configValues, ',');
        comSuperModuleConfigVos.forEach(vo -> {
            ComSuperModuleConfigExistVo configExistVo = new ComSuperModuleConfigExistVo();
            configExistVo.setIcon(vo.getIcon());
            configExistVo.setColor(vo.getColor());
            configExistVo.setName(vo.getName());
            configExistVo.setSort(vo.getSort());
            configExistVo.setId(vo.getId());
            configExistVo.setValue(vo.getValue());
            configExistVo.setOpenWay(vo.getOpenWay());
            if (list.contains(vo.getId().toString())) {
                configExistVo.setExistStatus(0);
            } else {
                configExistVo.setExistStatus(1);
            }
            convert.add(configExistVo);
        });
        return convert;

    }

    @Override
    public void addUserConfigByBo(ComUserConfigUpdateBo bo) {
        ComUserConfigVo comUserConfigVo = baseMapper.selectVoById(bo.getId());
        String configValues = comUserConfigVo.getConfigValues();
        List<String> list = StrUtil.splitTrim(configValues, ',');
        if(list.contains(bo.getConfigValueId())){
            throw new BaseException(ErrorMsg.ERR_COM_CAN_NOT_REPEAT.getMessage());
        }
        list.add(bo.getConfigValueId());
        comUserConfigVo.setConfigValues(String.join(",",list));
        ComUserConfig comUserConfig = MapstructUtils.convert(comUserConfigVo,ComUserConfig.class);
        baseMapper.updateById(comUserConfig);
    }

    @Override
    public void removeUserConfigByBo(ComUserConfigUpdateBo bo) {
        ComUserConfigVo comUserConfigVo = baseMapper.selectVoById(bo.getId());
        String configValues = comUserConfigVo.getConfigValues();
        List<String> list = StrUtil.splitTrim(configValues, ',');
        if(!list.contains(bo.getConfigValueId())){
            throw new BaseException(ErrorMsg.ERR_COM_DATA_NOT_EXIST.getMessage());
        }
        list.remove(bo.getConfigValueId());
        comUserConfigVo.setConfigValues(String.join(",",list));
        ComUserConfig comUserConfig = MapstructUtils.convert(comUserConfigVo,ComUserConfig.class);
        baseMapper.updateById(comUserConfig);
    }

}
