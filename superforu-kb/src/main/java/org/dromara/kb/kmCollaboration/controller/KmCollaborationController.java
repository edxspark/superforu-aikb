package org.dromara.kb.kmCollaboration.controller;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckLogin;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.kb.km.domain.bo.KmBo;
import org.dromara.kb.km.domain.vo.KmVo;
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
import org.dromara.kb.kmCollaboration.domain.vo.KmCollaborationVo;
import org.dromara.kb.kmCollaboration.domain.bo.KmCollaborationBo;
import org.dromara.kb.kmCollaboration.service.IKmCollaborationService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 协同管理
 *
 * @author zzg
 * @date 2023-12-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/kb/kmCollaboration")
public class KmCollaborationController extends BaseController {

    private final IKmCollaborationService kmCollaborationService;

    /**
     * 查询协同管理列表
     */
    @SaCheckLogin
    @GetMapping("/list")
    public TableDataInfo<KmCollaborationVo> list(KmCollaborationBo bo, PageQuery pageQuery) {
        return kmCollaborationService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出协同管理列表
     */
    @SaCheckLogin
    @Log(title = "协同管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(KmCollaborationBo bo, HttpServletResponse response) {
        List<KmCollaborationVo> list = kmCollaborationService.queryList(bo);
        ExcelUtil.exportExcel(list, "协同管理", KmCollaborationVo.class, response);
    }

    /**
     * 获取协同管理详细信息
     *
     * @param id 主键
     */
    @SaCheckLogin
    @GetMapping("/{id}")
    public R<KmCollaborationVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(kmCollaborationService.queryById(id));
    }

    /**
     * 新增协同管理
     */
    @SaCheckLogin
    @Log(title = "协同管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<KmCollaborationVo> add(@Validated(AddGroup.class) @RequestBody KmCollaborationBo bo) {
        return kmCollaborationService.insertByBo(bo);
    }

    /**
     * 修改协同管理
     */
    @SaCheckLogin
    @Log(title = "协同管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody KmCollaborationBo bo) {
        return toAjax(kmCollaborationService.updateByBo(bo));
    }

    /**
     * 删除协同管理
     *
     * @param ids 主键串
     */
    @SaCheckLogin
    @Log(title = "协同管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(kmCollaborationService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 不分页查询知识库团队协作
     * @param bo 团队协作检索对象
     * @author zzg
     */
    @SaCheckLogin
    @GetMapping("/listAll")
    public R listAll(KmCollaborationBo bo) {
        List<KmCollaborationVo> list = kmCollaborationService.queryList(bo);
        return R.ok(list);
    }

    /**
     * 据知识库ID获取团队协助集合信息
     * @param linkKmId 知识库id
     * @author zzg
     */
    @SaCheckLogin
    @GetMapping("/listByLinkKmId")
    public R listByLinkKmId(Long linkKmId) {
        List<KmCollaborationVo> list = kmCollaborationService.listByLinkKmId(linkKmId);
        return R.ok(list);
    }
}
