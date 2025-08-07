package org.dromara.com.userConfig.controller;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckLogin;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.com.quickEntranceConfig.domain.vo.ComQuickEntranceConfigExistVo;
import org.dromara.com.superModuleConfig.domain.vo.ComSuperModuleConfigExistVo;
import org.dromara.com.userConfig.domain.bo.ComUserConfigQueryBo;
import org.dromara.com.userConfig.domain.bo.ComUserConfigUpdateBo;
import org.dromara.com.userConfig.domain.vo.ComUserConfigQueryVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.com.userConfig.domain.vo.ComUserConfigVo;
import org.dromara.com.userConfig.domain.bo.ComUserConfigBo;
import org.dromara.com.userConfig.service.IComUserConfigService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 用户配置
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/com/userConfig")
public class ComUserConfigController extends BaseController {

    private final IComUserConfigService comUserConfigService;



    /**
     * 查询用户配置列表
     */
    @SaCheckLogin
    @GetMapping("/list")
    public R<ComUserConfigQueryVo> queryList() {
        return R.ok(comUserConfigService.queryList());
    }




    /**
     * 查询快捷入口列表是否已添加
     * @return
     */
    @SaCheckLogin
    @PostMapping("/queryQuickEntranceConfigListByUser")
    public R<List<ComQuickEntranceConfigExistVo>> queryQuickEntranceListByUser(){
        return R.ok(comUserConfigService.queryQuickEntranceConfigExistList());
    }


    /**
     * 查询超级模块列表是否已添加
     * @return
     */
    @SaCheckLogin
    @PostMapping("/querySuperModuleConfigListByUser")
    public R<List<ComSuperModuleConfigExistVo>> querySuperModuleListByUser(){
        return R.ok(comUserConfigService.querySuperModuleConfigExistList());
    }

    /**
     * 新增用户配置
     */
    @SaCheckLogin
    @Log(title = "新增用户配置", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/add")
    public R<Void> addUserConfig(@Validated(EditGroup.class) @RequestBody ComUserConfigUpdateBo bo) {
        comUserConfigService.addUserConfigByBo(bo);
        return R.ok();
    }



    /**
     * 移除用户配置
     */
    @SaCheckLogin
    @Log(title = "移除用户配置", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/remove")
    public R<Void> removeUserConfig(@Validated(EditGroup.class) @RequestBody ComUserConfigUpdateBo bo) {
        comUserConfigService.removeUserConfigByBo(bo);
        return R.ok();
    }
}
