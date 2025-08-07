package org.dromara.kb.fileTemplateType.controller;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckLogin;
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
import org.dromara.kb.fileTemplateType.domain.vo.FileTemplateTypeVo;
import org.dromara.kb.fileTemplateType.domain.bo.FileTemplateTypeBo;
import org.dromara.kb.fileTemplateType.service.IFileTemplateTypeService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 文档模板类型
 *
 * @author Lion Li
 * @date 2023-12-12
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/kb/fileTemplateType")
public class FileTemplateTypeController extends BaseController {

    private final IFileTemplateTypeService fileTemplateTypeService;

    /**
     * 查询文档模板类型列表
     */
    @SaCheckLogin
    @GetMapping("/list")
    public TableDataInfo<FileTemplateTypeVo> list(FileTemplateTypeBo bo, PageQuery pageQuery) {
        return fileTemplateTypeService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出文档模板类型列表
     */
    @SaCheckPermission("kb:fileTemplateType:export")
    @Log(title = "文档模板类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FileTemplateTypeBo bo, HttpServletResponse response) {
        List<FileTemplateTypeVo> list = fileTemplateTypeService.queryList(bo);
        ExcelUtil.exportExcel(list, "文档模板类型", FileTemplateTypeVo.class, response);
    }

    /**
     * 获取文档模板类型详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("kb:fileTemplateType:query")
    @GetMapping("/{id}")
    public R<FileTemplateTypeVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(fileTemplateTypeService.queryById(id));
    }

    /**
     * 新增文档模板类型
     */
    @SaCheckPermission("kb:fileTemplateType:add")
    @Log(title = "文档模板类型", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FileTemplateTypeBo bo) {
        return toAjax(fileTemplateTypeService.insertByBo(bo));
    }

    /**
     * 修改文档模板类型
     */
    @SaCheckPermission("kb:fileTemplateType:edit")
    @Log(title = "文档模板类型", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FileTemplateTypeBo bo) {
        return toAjax(fileTemplateTypeService.updateByBo(bo));
    }

    /**
     * 删除文档模板类型
     *
     * @param ids 主键串
     */
    @SaCheckPermission("kb:fileTemplateType:remove")
    @Log(title = "文档模板类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(fileTemplateTypeService.deleteWithValidByIds(List.of(ids), true));
    }
}
