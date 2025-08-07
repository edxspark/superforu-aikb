package org.dromara.com.msgCenter.controller;

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
import org.dromara.com.msgCenter.domain.vo.ComMsgCenterVo;
import org.dromara.com.msgCenter.domain.bo.ComMsgCenterBo;
import org.dromara.com.msgCenter.service.IComMsgCenterService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 消息中心
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/com/msgCenter")
public class ComMsgCenterController extends BaseController {

    private final IComMsgCenterService comMsgCenterService;

    /**
     * 查询消息中心列表
     */
    @SaCheckPermission("com:msgCenter:list")
    @GetMapping("/list")
    public TableDataInfo<ComMsgCenterVo> list(ComMsgCenterBo bo, PageQuery pageQuery) {
        return comMsgCenterService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出消息中心列表
     */
    @SaCheckPermission("com:msgCenter:export")
    @Log(title = "消息中心", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ComMsgCenterBo bo, HttpServletResponse response) {
        List<ComMsgCenterVo> list = comMsgCenterService.queryList(bo);
        ExcelUtil.exportExcel(list, "消息中心", ComMsgCenterVo.class, response);
    }

    /**
     * 获取消息中心详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("com:msgCenter:query")
    @GetMapping("/{id}")
    public R<ComMsgCenterVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(comMsgCenterService.queryById(id));
    }

    /**
     * 新增消息中心
     */
    @SaCheckPermission("com:msgCenter:add")
    @Log(title = "消息中心", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ComMsgCenterBo bo) {
        return toAjax(comMsgCenterService.insertByBo(bo));
    }

    /**
     * 修改消息中心
     */
    @SaCheckPermission("com:msgCenter:edit")
    @Log(title = "消息中心", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ComMsgCenterBo bo) {
        return toAjax(comMsgCenterService.updateByBo(bo));
    }

    /**
     * 删除消息中心
     *
     * @param ids 主键串
     */
    @SaCheckPermission("com:msgCenter:remove")
    @Log(title = "消息中心", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(comMsgCenterService.deleteWithValidByIds(List.of(ids), true));
    }
}
