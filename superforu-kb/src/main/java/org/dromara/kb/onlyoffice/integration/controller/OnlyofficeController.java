package org.dromara.kb.onlyoffice.integration.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.web.core.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Onlyoffice
 *
 * @author moks.mo
 * @date 2024-03-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/kb/onlyoffice/")
public class OnlyofficeController extends BaseController {

    /** 应用ID */
    @Value("${wxpay.appId}")
    private  String appId;

    /**
     * 获取token
     */
    @SaIgnore
    @Log(title = "获取token", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public String getToken() {
        return null;
    }

}
