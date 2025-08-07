package org.dromara.kb.fileHistory.controller;

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
import org.dromara.kb.fileHistory.domain.vo.FileHistoryVo;
import org.dromara.kb.fileHistory.domain.bo.FileHistoryBo;
import org.dromara.kb.fileHistory.service.IFileHistoryService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 文件历史
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/kb/fileHistory")
public class FileHistoryController extends BaseController {

    private final IFileHistoryService fileHistoryService;

    /**
     * 查询文件历史列表
     */
    @SaCheckPermission("kb:fileHistory:list")
    @GetMapping("/list")
    public TableDataInfo<FileHistoryVo> list(FileHistoryBo bo, PageQuery pageQuery) {
        return fileHistoryService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出文件历史列表
     */
    @SaCheckPermission("kb:fileHistory:export")
    @Log(title = "文件历史", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FileHistoryBo bo, HttpServletResponse response) {
        List<FileHistoryVo> list = fileHistoryService.queryList(bo);
        ExcelUtil.exportExcel(list, "文件历史", FileHistoryVo.class, response);
    }

    /**
     * 获取文件历史详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("kb:fileHistory:query")
    @GetMapping("/{id}")
    public R<FileHistoryVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(fileHistoryService.queryById(id));
    }

    /**
     * 新增文件历史
     */
    @SaCheckPermission("kb:fileHistory:add")
    @Log(title = "文件历史", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FileHistoryBo bo) {
        return toAjax(fileHistoryService.insertByBo(bo));
    }

    /**
     * 修改文件历史
     */
    @SaCheckPermission("kb:fileHistory:edit")
    @Log(title = "文件历史", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FileHistoryBo bo) {
        return toAjax(fileHistoryService.updateByBo(bo));
    }

    /**
     * 删除文件历史
     *
     * @param ids 主键串
     */
    @SaCheckPermission("kb:fileHistory:remove")
    @Log(title = "文件历史", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(fileHistoryService.deleteWithValidByIds(List.of(ids), true));
    }
}
