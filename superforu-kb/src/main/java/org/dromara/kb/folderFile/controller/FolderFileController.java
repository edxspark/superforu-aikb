package org.dromara.kb.folderFile.controller;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.kb.fileContent.service.IFileContentService;
import org.dromara.kb.fileType.domain.vo.FileTypeVo;
import org.dromara.kb.folderFile.domain.bo.FolderFileRenameBo;
import org.dromara.kb.folderFile.domain.bo.FolderFileTemplateBo;
import org.dromara.kb.km.service.IKmService;
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
import org.dromara.kb.folderFile.domain.vo.FolderFileVo;
import org.dromara.kb.folderFile.domain.bo.FolderFileBo;
import org.dromara.kb.folderFile.service.IFolderFileService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 文件夹&文件
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/kb/folderFile")
public class FolderFileController extends BaseController {

    private final IFolderFileService folderFileService;
    private final IKmService iKmService;


    /**
     * 文档向量华
     */
    @SaCheckLogin
    @PostMapping("/embedding/{fileId}")
    public R<String> embedding(@PathVariable Long fileId) {
        boolean rt = iKmService.kmEmbeddingSingleFile(fileId);
        R<String> rtData = new R<String>();
        rtData.setData(""+rt);
        rtData.setMsg("Success");
        rtData.setCode(200);
        return rtData;
    }


    /**
     * ES文档查询
     */
    @SaCheckLogin
    @PostMapping("/fileESSearch")
    public R<String> fileESSearch(@RequestParam("key") String key) {
         String rt = folderFileService.fileESSearch(key);
         R<String> rtData = new R<String>();
         rtData.setData(rt);
         rtData.setMsg("Success");
         rtData.setCode(200);
        return rtData;
    }

    /**
     * 查询最近编辑文件列表
     */
    @SaCheckLogin
    @GetMapping("/listRecentlyEdit")
    public TableDataInfo<FolderFileVo> listRecentlyEdit(FolderFileBo bo, PageQuery pageQuery) {
        return folderFileService.queryPageRecentlyEdit(bo, pageQuery);
    }


    /**
     * 查询文件夹&文件列表
     */
    @SaCheckLogin
    @GetMapping("/list")
    public TableDataInfo<FolderFileVo> list(FolderFileBo bo, PageQuery pageQuery) {
        return folderFileService.queryPageList(bo, pageQuery);
    }


    /**
     * 新增文件夹&文件
     */
    @SaCheckLogin
    @Log(title = "文件夹&文件", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Long> add(@Validated(AddGroup.class) @RequestBody FolderFileBo bo) {
        return folderFileService.insertByBo(bo);
    }

    /**
     * 使用模版创建文件
     */
    @SaCheckLogin
    @Log(title = "使用模版创建文件", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/createByTemplate")
    public R<FolderFileVo> addByTemplatte(@Validated(AddGroup.class) @RequestBody FolderFileTemplateBo bo) {
        return folderFileService.insertByTemplateBo(bo);
    }

    /**
     * 重命名文件夹&文件
     */
    @SaCheckLogin
    @Log(title = "重命名文件夹&文件", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolderFileRenameBo bo) {
        return toAjax(folderFileService.updateByBo(bo));
    }

    /**
     * 删除文件夹&文件
     *
     * @param ids 主键串
     */
    @SaCheckLogin
    @Log(title = "文件夹&文件", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(folderFileService.deleteWithValidByIds(List.of(ids), true,null));
    }

    /**
     * 获取文件类型详细信息
     *
     * @param id 主键
     */
    @SaCheckLogin
    @GetMapping("/{id}")
    public R<FolderFileVo> getInfo(@NotNull(message = "主键不能为空")
                                 @PathVariable Long id) {
        return R.ok(folderFileService.queryById(id));
    }

    /**
     * 获取分享文件信息
     *
     * @param shareId 分享ID
     * @param fileId 文件ID
     */
    @SaIgnore
    @GetMapping("/getShare/{shareId}/{fileId}")
    public R<FolderFileVo> getShareFile(@NotNull(message = "分享ID不能为空")
                                   @PathVariable String shareId,@NotNull(message = "文件ID不能为空") @PathVariable Long fileId) {
        return R.ok(folderFileService.queryShareFolderFileByShareId(shareId,fileId));
    }



}
