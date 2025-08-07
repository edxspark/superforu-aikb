package org.dromara.kb.fileRecently.controller;

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
import org.dromara.kb.fileRecently.domain.vo.FileRecentlyVo;
import org.dromara.kb.fileRecently.domain.bo.FileRecentlyBo;
import org.dromara.kb.fileRecently.service.IFileRecentlyService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 最近编辑
 *
 * @author Lion Li
 * @date 2023-12-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/kb/fileRecently")
public class FileRecentlyController extends BaseController {

    private final IFileRecentlyService fileRecentlyService;

    /**
     * 查询最近编辑列表
     */
    @SaCheckLogin
    @GetMapping("/list")
    public TableDataInfo<FileRecentlyVo> list(FileRecentlyBo bo, PageQuery pageQuery) {
        return fileRecentlyService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出最近编辑列表
     */
    @SaCheckPermission("kb:fileRecently:export")
    @Log(title = "最近编辑", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FileRecentlyBo bo, HttpServletResponse response) {
        List<FileRecentlyVo> list = fileRecentlyService.queryList(bo);
        ExcelUtil.exportExcel(list, "最近编辑", FileRecentlyVo.class, response);
    }

    /**
     * 获取最近编辑详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("kb:fileRecently:query")
    @GetMapping("/{id}")
    public R<FileRecentlyVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(fileRecentlyService.queryById(id));
    }

    /**
     * 新增最近编辑
     */
    @SaCheckPermission("kb:fileRecently:add")
    @Log(title = "最近编辑", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FileRecentlyBo bo) {
        return toAjax(fileRecentlyService.insertByBo(bo));
    }

    /**
     * 修改最近编辑
     */
    @SaCheckPermission("kb:fileRecently:edit")
    @Log(title = "最近编辑", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FileRecentlyBo bo) {
        return toAjax(fileRecentlyService.updateByBo(bo));
    }

    /**
     * 删除最近编辑
     *
     * @param ids 主键串
     */
    @SaCheckPermission("kb:fileRecently:remove")
    @Log(title = "最近编辑", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(fileRecentlyService.deleteWithValidByIds(List.of(ids), true));
    }
}
