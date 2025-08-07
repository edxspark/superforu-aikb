package org.dromara.kb.fileType.controller;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckLogin;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.system.domain.vo.SysDictDataVo;
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
import org.dromara.kb.fileType.domain.vo.FileTypeVo;
import org.dromara.kb.fileType.domain.bo.FileTypeBo;
import org.dromara.kb.fileType.service.IFileTypeService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 文件类型
 *
 * @author Lion Li
 * @date 2023-12-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/kb/fileType")
public class FileTypeController extends BaseController {

    private final IFileTypeService fileTypeService;

    /**
     * 查询文件类型列表
     */
    @SaCheckLogin
    @GetMapping("/list")
    public TableDataInfo<SysDictDataVo> list(FileTypeBo bo, PageQuery pageQuery) {
        return fileTypeService.queryPageList(bo, pageQuery);
    }


    /**
     * 获取文件类型详细信息
     *
     * @param id 主键
     */
    @SaCheckLogin
    @GetMapping("/{id}")
    public R<FileTypeVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(fileTypeService.queryById(id));
    }

    /**
     * 新增文件类型
     */
    @SaCheckLogin
    @Log(title = "文件类型", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FileTypeBo bo) {
        return toAjax(fileTypeService.insertByBo(bo));
    }

    /**
     * 修改文件类型
     */
    @SaCheckLogin
    @Log(title = "文件类型", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FileTypeBo bo) {
        return toAjax(fileTypeService.updateByBo(bo));
    }

    /**
     * 删除文件类型
     *
     * @param ids 主键串
     */
    @SaCheckLogin
    @Log(title = "文件类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(fileTypeService.deleteWithValidByIds(List.of(ids), true));
    }
}
