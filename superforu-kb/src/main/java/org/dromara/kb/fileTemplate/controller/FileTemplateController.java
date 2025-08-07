package org.dromara.kb.fileTemplate.controller;

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
import org.dromara.kb.fileTemplate.domain.vo.FileTemplateVo;
import org.dromara.kb.fileTemplate.domain.bo.FileTemplateBo;
import org.dromara.kb.fileTemplate.service.IFileTemplateService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 文档模板
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/kb/fileTemplate")
public class FileTemplateController extends BaseController {

    private final IFileTemplateService fileTemplateService;

    /**
     * 查询文档模板列表
     */
    @SaCheckLogin
    @GetMapping("/list")
    public TableDataInfo<FileTemplateVo> list(FileTemplateBo bo, PageQuery pageQuery) {
        return fileTemplateService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出文档模板列表
     */
    @SaCheckLogin
    @Log(title = "文档模板", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FileTemplateBo bo, HttpServletResponse response) {
        List<FileTemplateVo> list = fileTemplateService.queryList(bo);
        ExcelUtil.exportExcel(list, "文档模板", FileTemplateVo.class, response);
    }

    /**
     * 获取文档模板详细信息
     *
     * @param id 主键
     */
    @SaCheckLogin
    @GetMapping("/{id}")
    public R<FileTemplateVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(fileTemplateService.queryById(id));
    }

    /**
     * 新增文档模板
     */
    @SaCheckLogin
    @Log(title = "文档模板", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FileTemplateBo bo) {
        return toAjax(fileTemplateService.insertByBo(bo));
    }

    /**
     * 修改文档模板
     */
    @SaCheckLogin
    @Log(title = "文档模板", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FileTemplateBo bo) {
        return toAjax(fileTemplateService.updateByBo(bo));
    }

    /**
     * 删除文档模板
     *
     * @param ids 主键串
     */
    @SaCheckLogin
    @Log(title = "文档模板", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(fileTemplateService.deleteWithValidByIds(List.of(ids), true));
    }
}
