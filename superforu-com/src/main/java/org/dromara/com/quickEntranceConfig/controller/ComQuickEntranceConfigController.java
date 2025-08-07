package org.dromara.com.quickEntranceConfig.controller;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckLogin;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.com.quickEntranceConfig.domain.bo.ComQuickEntranceConfigAddBo;
import org.dromara.com.quickEntranceConfig.domain.bo.ComQuickEntranceConfigEditBo;
import org.dromara.com.quickEntranceConfig.domain.vo.ComQuickEntranceConfigExistVo;
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
import org.dromara.com.quickEntranceConfig.domain.vo.ComQuickEntranceConfigVo;
import org.dromara.com.quickEntranceConfig.domain.bo.ComQuickEntranceConfigBo;
import org.dromara.com.quickEntranceConfig.service.IComQuickEntranceConfigService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 快捷入口配置
 *
 * @author JackLiao
 * @date 2023-12-11
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/com/quickEntranceConfig")
public class ComQuickEntranceConfigController extends BaseController {

    private final IComQuickEntranceConfigService comQuickEntranceConfigService;

    /**
     * 查询快捷入口配置列表
     */
    @SaCheckLogin
    @GetMapping("/list")
    public TableDataInfo<ComQuickEntranceConfigVo> list(PageQuery pageQuery) {
        return comQuickEntranceConfigService.queryPageList(pageQuery);
    }


    /**
     * 新增快捷入口配置
     */
    @SaCheckLogin
    @Log(title = "快捷入口配置", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ComQuickEntranceConfigAddBo bo) {
        return toAjax(comQuickEntranceConfigService.insertByBo(bo));
    }

    /**
     * 新增快捷入口配置
     */
    @SaCheckLogin
    @Log(title = "快捷入口配置", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("")
    public R<Void> addData(@Validated(AddGroup.class) @RequestBody ComQuickEntranceConfigAddBo bo) {
        return toAjax(comQuickEntranceConfigService.insertByBo(bo));
    }

    /**
     * 修改快捷入口配置
     */
    @SaCheckLogin
    @Log(title = "快捷入口配置", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ComQuickEntranceConfigEditBo bo) {
        return toAjax(comQuickEntranceConfigService.updateByBo(bo));
    }



}
