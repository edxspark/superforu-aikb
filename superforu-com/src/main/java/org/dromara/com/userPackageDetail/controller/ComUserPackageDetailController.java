package org.dromara.com.userPackageDetail.controller;

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
import org.dromara.com.userPackageDetail.domain.vo.ComUserPackageDetailVo;
import org.dromara.com.userPackageDetail.domain.bo.ComUserPackageDetailBo;
import org.dromara.com.userPackageDetail.service.IComUserPackageDetailService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 用户权益资源套餐明细
 *
 * @author Lion Li
 * @date 2024-03-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/com/userPackageDetail")
public class ComUserPackageDetailController extends BaseController {

    private final IComUserPackageDetailService comUserPackageDetailService;

    /**
     * 查询用户权益资源套餐明细列表
     */
    @SaCheckPermission("com:userPackageDetail:list")
    @GetMapping("/list")
    public TableDataInfo<ComUserPackageDetailVo> list(ComUserPackageDetailBo bo, PageQuery pageQuery) {
        return comUserPackageDetailService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出用户权益资源套餐明细列表
     */
    @SaCheckPermission("com:userPackageDetail:export")
    @Log(title = "用户权益资源套餐明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ComUserPackageDetailBo bo, HttpServletResponse response) {
        List<ComUserPackageDetailVo> list = comUserPackageDetailService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户权益资源套餐明细", ComUserPackageDetailVo.class, response);
    }

    /**
     * 获取用户权益资源套餐明细详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("com:userPackageDetail:query")
    @GetMapping("/{id}")
    public R<ComUserPackageDetailVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(comUserPackageDetailService.queryById(id));
    }

    /**
     * 新增用户权益资源套餐明细
     */
    @SaCheckPermission("com:userPackageDetail:add")
    @Log(title = "用户权益资源套餐明细", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ComUserPackageDetailBo bo) {
        return toAjax(comUserPackageDetailService.insertByBo(bo));
    }

    /**
     * 修改用户权益资源套餐明细
     */
    @SaCheckPermission("com:userPackageDetail:edit")
    @Log(title = "用户权益资源套餐明细", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ComUserPackageDetailBo bo) {
        return toAjax(comUserPackageDetailService.updateByBo(bo));
    }

    /**
     * 删除用户权益资源套餐明细
     *
     * @param ids 主键串
     */
    @SaCheckPermission("com:userPackageDetail:remove")
    @Log(title = "用户权益资源套餐明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(comUserPackageDetailService.deleteWithValidByIds(List.of(ids), true));
    }
}
