package org.dromara.com.user.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import lombok.RequiredArgsConstructor;
import org.dromara.com.utils.loginUtil.LoginUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.com.user.domain.vo.ComUserVo;
import org.dromara.com.user.domain.bo.ComUserBo;
import org.dromara.com.user.service.IComUserService;

/**
 * 用户信息
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/com/user")
public class ComUserController extends BaseController {

    private final IComUserService comUserService;


    /**
     * 修改用户信息
     */
    @SaCheckLogin
    @Log(title = "用户信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ComUserBo bo) {
        return toAjax(comUserService.updateByBo(bo));
    }


    /**
     * 获取用户信息详细信息
     *
     */
    @SaCheckLogin
    @GetMapping("/getUserInfo")
    public R<ComUserVo> getUserInfo() {
        return R.ok(comUserService.queryUserInfo(LoginUtil.getUserId()));
    }




}
