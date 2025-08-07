package org.dromara.pay.callback.controller;

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
import org.dromara.pay.callback.domain.vo.PayCallbackVo;
import org.dromara.pay.callback.domain.bo.PayCallbackBo;
import org.dromara.pay.callback.service.IPayCallbackService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 支付回调
 *
 * @author Lion Li
 * @date 2024-03-22
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/pay/callback")
public class PayCallbackController extends BaseController {

    private final IPayCallbackService payCallbackService;

    /**
     * 查询支付回调列表
     */
    @SaCheckPermission("pay:callback:list")
    @GetMapping("/list")
    public TableDataInfo<PayCallbackVo> list(PayCallbackBo bo, PageQuery pageQuery) {
        return payCallbackService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出支付回调列表
     */
    @SaCheckPermission("pay:callback:export")
    @Log(title = "支付回调", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(PayCallbackBo bo, HttpServletResponse response) {
        List<PayCallbackVo> list = payCallbackService.queryList(bo);
        ExcelUtil.exportExcel(list, "支付回调", PayCallbackVo.class, response);
    }

    /**
     * 获取支付回调详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("pay:callback:query")
    @GetMapping("/{id}")
    public R<PayCallbackVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(payCallbackService.queryById(id));
    }

    /**
     * 新增支付回调
     */
    @SaCheckPermission("pay:callback:add")
    @Log(title = "支付回调", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody PayCallbackBo bo) {
        return toAjax(payCallbackService.insertByBo(bo));
    }

    /**
     * 修改支付回调
     */
    @SaCheckPermission("pay:callback:edit")
    @Log(title = "支付回调", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody PayCallbackBo bo) {
        return toAjax(payCallbackService.updateByBo(bo));
    }

    /**
     * 删除支付回调
     *
     * @param ids 主键串
     */
    @SaCheckPermission("pay:callback:remove")
    @Log(title = "支付回调", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(payCallbackService.deleteWithValidByIds(List.of(ids), true));
    }
}
