package org.dromara.pay.order.controller;

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
import org.dromara.pay.order.domain.vo.PayOrderVo;
import org.dromara.pay.order.domain.bo.PayOrderBo;
import org.dromara.pay.order.service.IPayOrderService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 支付订单
 *
 * @author Lion Li
 * @date 2024-03-22
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/pay/order")
public class PayOrderController extends BaseController {

    private final IPayOrderService payOrderService;

    /**
     * 查询支付订单列表
     */
    @SaCheckPermission("pay:order:list")
    @GetMapping("/list")
    public TableDataInfo<PayOrderVo> list(PayOrderBo bo, PageQuery pageQuery) {
        return payOrderService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出支付订单列表
     */
    @SaCheckPermission("pay:order:export")
    @Log(title = "支付订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(PayOrderBo bo, HttpServletResponse response) {
        List<PayOrderVo> list = payOrderService.queryList(bo);
        ExcelUtil.exportExcel(list, "支付订单", PayOrderVo.class, response);
    }

    /**
     * 获取支付订单详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("pay:order:query")
    @GetMapping("/{id}")
    public R<PayOrderVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(payOrderService.queryById(id));
    }

    /**
     * 新增支付订单
     */
    @SaCheckPermission("pay:order:add")
    @Log(title = "支付订单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody PayOrderBo bo) {
        return toAjax(payOrderService.insertByBo(bo));
    }

    /**
     * 修改支付订单
     */
    @SaCheckPermission("pay:order:edit")
    @Log(title = "支付订单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody PayOrderBo bo) {
        return toAjax(payOrderService.updateByBo(bo));
    }

    /**
     * 删除支付订单
     *
     * @param ids 主键串
     */
    @SaCheckPermission("pay:order:remove")
    @Log(title = "支付订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(payOrderService.deleteWithValidByIds(List.of(ids), true));
    }
}
