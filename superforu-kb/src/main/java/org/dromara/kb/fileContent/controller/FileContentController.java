package org.dromara.kb.fileContent.controller;

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
import org.dromara.kb.fileContent.domain.vo.FileContentVo;
import org.dromara.kb.fileContent.domain.bo.FileContentBo;
import org.dromara.kb.fileContent.service.IFileContentService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 文件内容
 *
 * @author moks.mo
 * @date 2023-11-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/kb/fileContent")
public class FileContentController extends BaseController {

    private final IFileContentService fileContentService;


    /**
     * 获取文件内容详细信息
     *
     * @param id 主键
     */
    @SaCheckLogin
    @GetMapping("/{id}")
    public R<FileContentVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(fileContentService.queryById(id));
    }


    /**
     * 修改文件内容
     */
    @SaCheckLogin
    @Log(title = "文件内容", businessType = BusinessType.UPDATE)
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FileContentBo bo) {
        return toAjax(fileContentService.updateByBo(bo));
    }



}
