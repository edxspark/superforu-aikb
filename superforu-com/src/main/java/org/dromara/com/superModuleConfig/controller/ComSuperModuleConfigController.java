package org.dromara.com.superModuleConfig.controller;



import cn.dev33.satoken.annotation.SaCheckLogin;
import lombok.RequiredArgsConstructor;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.com.superModuleConfig.domain.bo.ComSuperModuleConfigEditBo;
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
import org.dromara.com.superModuleConfig.domain.vo.ComSuperModuleConfigVo;
import org.dromara.com.superModuleConfig.domain.bo.ComSuperModuleConfigBo;
import org.dromara.com.superModuleConfig.service.IComSuperModuleConfigService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 超级模块配置
 *
 * @author JackLiao
 * @date 2023-12-11
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/com/superModuleConfig")
public class ComSuperModuleConfigController extends BaseController {

    private final IComSuperModuleConfigService comSuperModuleConfigService;

    /**
     * 查询超级模块配置列表
     */
    @SaCheckLogin
    @GetMapping("/list")
    public TableDataInfo<ComSuperModuleConfigVo> list(ComSuperModuleConfigBo bo, PageQuery pageQuery) {
        return comSuperModuleConfigService.queryPageList(bo, pageQuery);
    }


    /**
     * 获取超级模块配置详细信息
     *
     * @param id 主键
     */
    @SaCheckLogin
    @GetMapping("/{id}")
    public R<ComSuperModuleConfigVo> getInfo(@NotNull(message = "主键不能为空")
                                             @PathVariable Long id) {
        return R.ok(comSuperModuleConfigService.queryById(id));
    }

    /**
     * 新增超级模块配置
     */
    @SaCheckLogin
    @Log(title = "超级模块配置", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ComSuperModuleConfigBo bo) {
        return toAjax(comSuperModuleConfigService.insertByBo(bo));
    }

    /**
     * 新增超级模块配置
     */
    @SaCheckLogin
    @Log(title = "超级模块配置", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> addRecord(@Validated(AddGroup.class) @RequestBody ComSuperModuleConfigBo bo) {
        return toAjax(comSuperModuleConfigService.insertByBo(bo));
    }

    /**
     * 修改超级模块配置
     */
    @SaCheckLogin
    @Log(title = "超级模块配置", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ComSuperModuleConfigEditBo bo) {
        return toAjax(comSuperModuleConfigService.updateByBo(bo));
    }

    /**
     * 修改超级模块配置
     */
    @SaCheckPermission("com:superModuleConfig:edit")
    @Log(title = "超级模块配置", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ComSuperModuleConfigBo bo) {
        return toAjax(comSuperModuleConfigService.updateByBo(bo));
    }

}
