package org.dromara.com.menuConfig.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
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
import org.dromara.com.menuConfig.domain.vo.ComMenuConfigVo;
import org.dromara.com.menuConfig.domain.bo.ComMenuConfigBo;
import org.dromara.com.menuConfig.service.IComMenuConfigService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 菜单配置
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/com/menuConfig")
public class ComMenuConfigController extends BaseController {

    private final IComMenuConfigService comMenuConfigService;

    /**
     * 查询菜单配置列表
     */
    @SaCheckPermission("com:menuConfig:list")
    @GetMapping("/list")
    public TableDataInfo<ComMenuConfigVo> list(ComMenuConfigBo bo, PageQuery pageQuery) {
        return comMenuConfigService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出菜单配置列表
     */
    @SaCheckPermission("com:menuConfig:export")
    @Log(title = "菜单配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ComMenuConfigBo bo, HttpServletResponse response) {
        List<ComMenuConfigVo> list = comMenuConfigService.queryList(bo);
        ExcelUtil.exportExcel(list, "菜单配置", ComMenuConfigVo.class, response);
    }

    /**
     * 获取菜单配置详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("com:menuConfig:query")
    @GetMapping("/{id}")
    public R<ComMenuConfigVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(comMenuConfigService.queryById(id));
    }

    /**
     * 新增菜单配置
     */
    @SaCheckPermission("com:menuConfig:add")
    @Log(title = "菜单配置", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ComMenuConfigBo bo) {
        return toAjax(comMenuConfigService.insertByBo(bo));
    }

    /**
     * 修改菜单配置
     */
    @SaCheckPermission("com:menuConfig:edit")
    @Log(title = "菜单配置", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ComMenuConfigBo bo) {
        return toAjax(comMenuConfigService.updateByBo(bo));
    }

    /**
     * 删除菜单配置
     *
     * @param ids 主键串
     */
    @SaCheckPermission("com:menuConfig:remove")
    @Log(title = "菜单配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(comMenuConfigService.deleteWithValidByIds(List.of(ids), true));
    }
}
