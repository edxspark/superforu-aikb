package org.dromara.kb.fileShare.controller;

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
import org.dromara.kb.fileShare.domain.vo.FileShareVo;
import org.dromara.kb.fileShare.domain.bo.FileShareBo;
import org.dromara.kb.fileShare.service.IFileShareService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 文件分享
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/kb/fileShare")
public class FileShareController extends BaseController {

    private final IFileShareService fileShareService;

    /**
     * 查询文件分享列表
     */
    @SaCheckPermission("kb:fileShare:list")
    @GetMapping("/list")
    public TableDataInfo<FileShareVo> list(FileShareBo bo, PageQuery pageQuery) {
        return fileShareService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出文件分享列表
     */
    @SaCheckPermission("kb:fileShare:export")
    @Log(title = "文件分享", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FileShareBo bo, HttpServletResponse response) {
        List<FileShareVo> list = fileShareService.queryList(bo);
        ExcelUtil.exportExcel(list, "文件分享", FileShareVo.class, response);
    }

    /**
     * 获取文件分享详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("kb:fileShare:query")
    @GetMapping("/{id}")
    public R<FileShareVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(fileShareService.queryById(id));
    }

    /**
     * 新增文件分享
     */
    @SaCheckPermission("kb:fileShare:add")
    @Log(title = "文件分享", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FileShareBo bo) {
        return toAjax(fileShareService.insertByBo(bo));
    }

    /**
     * 修改文件分享
     */
    @SaCheckPermission("kb:fileShare:edit")
    @Log(title = "文件分享", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FileShareBo bo) {
        return toAjax(fileShareService.updateByBo(bo));
    }

    /**
     * 删除文件分享
     *
     * @param ids 主键串
     */
    @SaCheckPermission("kb:fileShare:remove")
    @Log(title = "文件分享", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(fileShareService.deleteWithValidByIds(List.of(ids), true));
    }
}
