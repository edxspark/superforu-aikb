package org.dromara.com.inviteHistory.controller;

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
import org.dromara.com.inviteHistory.domain.vo.ComInviteHistoryVo;
import org.dromara.com.inviteHistory.domain.bo.ComInviteHistoryBo;
import org.dromara.com.inviteHistory.service.IComInviteHistoryService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 邀请记录
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/com/inviteHistory")
public class ComInviteHistoryController extends BaseController {

    private final IComInviteHistoryService comInviteHistoryService;

    /**
     * 查询邀请记录列表
     */
    @SaCheckPermission("com:inviteHistory:list")
    @GetMapping("/list")
    public TableDataInfo<ComInviteHistoryVo> list(ComInviteHistoryBo bo, PageQuery pageQuery) {
        return comInviteHistoryService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出邀请记录列表
     */
    @SaCheckPermission("com:inviteHistory:export")
    @Log(title = "邀请记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ComInviteHistoryBo bo, HttpServletResponse response) {
        List<ComInviteHistoryVo> list = comInviteHistoryService.queryList(bo);
        ExcelUtil.exportExcel(list, "邀请记录", ComInviteHistoryVo.class, response);
    }

    /**
     * 获取邀请记录详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("com:inviteHistory:query")
    @GetMapping("/{id}")
    public R<ComInviteHistoryVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(comInviteHistoryService.queryById(id));
    }

    /**
     * 新增邀请记录
     */
    @SaCheckLogin
    @Log(title = "邀请记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ComInviteHistoryBo bo) {
        return toAjax(comInviteHistoryService.insertByBo(bo));
    }

    /**
     * 修改邀请记录
     */
    @SaCheckPermission("com:inviteHistory:edit")
    @Log(title = "邀请记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ComInviteHistoryBo bo) {
        return toAjax(comInviteHistoryService.updateByBo(bo));
    }

    /**
     * 删除邀请记录
     *
     * @param ids 主键串
     */
    @SaCheckPermission("com:inviteHistory:remove")
    @Log(title = "邀请记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(comInviteHistoryService.deleteWithValidByIds(List.of(ids), true));
    }
}
