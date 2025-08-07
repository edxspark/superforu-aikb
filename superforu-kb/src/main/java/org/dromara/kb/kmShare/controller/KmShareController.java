package org.dromara.kb.kmShare.controller;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.kb.kmCollaboration.domain.bo.KmCollaborationBo;
import org.dromara.kb.kmCollaboration.domain.vo.KmCollaborationVo;
import org.dromara.kb.kmShare.domain.KmShare;
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
import org.dromara.kb.kmShare.domain.vo.KmShareVo;
import org.dromara.kb.kmShare.domain.bo.KmShareBo;
import org.dromara.kb.kmShare.service.IKmShareService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 分享预览
 *
 * @author zzg
 * @date 2023-12-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/kb/kmShare")
public class KmShareController extends BaseController {

    private final IKmShareService kmShareService;

    /**
     * 查询分享预览列表
     */
    @SaCheckPermission("kb:kmShare:list")
    @GetMapping("/list")
    public TableDataInfo<KmShareVo> list(KmShareBo bo, PageQuery pageQuery) {
        return kmShareService.queryPageList(bo, pageQuery);
    }


    /**
     * 获取分享预览详细信息
     *
     * @param id 主键
     */
    @SaCheckLogin
    @GetMapping("/{id}")
    public R<KmShareVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(kmShareService.queryById(id));
    }

    /**
     * 获取分享预览详细信息
     *
     * @param shareId 分享ID
     */
    @SaIgnore
    @GetMapping ("/getKmShareInfo/{shareId}")
    public R<KmShareVo> queryByKmShareId(@PathVariable String shareId) {
        return R.ok(kmShareService.queryByKmShareId(shareId));
    }

    /**
     * 新增分享预览
     */
    @SaCheckLogin
    @Log(title = "分享预览", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<KmShareVo> add(@Validated(AddGroup.class) @RequestBody KmShareBo bo) {
        return kmShareService.insertByBo(bo);
    }

    /**
     * 修改分享预览
     */
    @SaCheckLogin
    @Log(title = "分享预览", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody KmShareBo bo) {
        return toAjax(kmShareService.updateByBo(bo));
    }

    /**
     * 删除分享预览
     *
     * @param ids 主键串
     */
    @SaCheckLogin
    @Log(title = "分享预览", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(kmShareService.deleteWithValidByIds(List.of(ids), true));
    }


    /**
     * 根据知识库ID获取分享信息
     * @param linkKmId 知识库ID
     * @author zzg
     */
    @SaCheckLogin
    @GetMapping("/getOneByLinkKmId")
    public R getOneByLinkKmId (Long linkKmId) {
        KmShareVo kmShareVo = kmShareService.getOneByLinkKmId(linkKmId);
        return R.ok(kmShareVo);
    }
}
