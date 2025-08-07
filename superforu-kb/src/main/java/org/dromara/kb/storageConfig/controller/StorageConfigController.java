package org.dromara.kb.storageConfig.controller;

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
import org.dromara.kb.storageConfig.domain.vo.StorageConfigVo;
import org.dromara.kb.storageConfig.domain.bo.StorageConfigBo;
import org.dromara.kb.storageConfig.service.IStorageConfigService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 集成存储配置
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/kb/storageConfig")
public class StorageConfigController extends BaseController {

    private final IStorageConfigService storageConfigService;

    /**
     * 查询集成存储配置列表
     */
    @SaCheckPermission("kb:storageConfig:list")
    @GetMapping("/list")
    public TableDataInfo<StorageConfigVo> list(StorageConfigBo bo, PageQuery pageQuery) {
        return storageConfigService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出集成存储配置列表
     */
    @SaCheckPermission("kb:storageConfig:export")
    @Log(title = "集成存储配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(StorageConfigBo bo, HttpServletResponse response) {
        List<StorageConfigVo> list = storageConfigService.queryList(bo);
        ExcelUtil.exportExcel(list, "集成存储配置", StorageConfigVo.class, response);
    }

    /**
     * 获取集成存储配置详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("kb:storageConfig:query")
    @GetMapping("/{id}")
    public R<StorageConfigVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(storageConfigService.queryById(id));
    }

    /**
     * 新增集成存储配置
     */
    @SaCheckPermission("kb:storageConfig:add")
    @Log(title = "集成存储配置", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody StorageConfigBo bo) {
        return toAjax(storageConfigService.insertByBo(bo));
    }

    /**
     * 修改集成存储配置
     */
    @SaCheckPermission("kb:storageConfig:edit")
    @Log(title = "集成存储配置", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody StorageConfigBo bo) {
        return toAjax(storageConfigService.updateByBo(bo));
    }

    /**
     * 删除集成存储配置
     *
     * @param ids 主键串
     */
    @SaCheckPermission("kb:storageConfig:remove")
    @Log(title = "集成存储配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(storageConfigService.deleteWithValidByIds(List.of(ids), true));
    }
}
