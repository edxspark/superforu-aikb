package org.dromara.com.userPackagePromotion.controller;

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
import org.dromara.com.userPackagePromotion.domain.vo.ComUserPackagePromotionVo;
import org.dromara.com.userPackagePromotion.domain.bo.ComUserPackagePromotionBo;
import org.dromara.com.userPackagePromotion.service.IComUserPackagePromotionService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 优惠码
 *
 * @author Lion Li
 * @date 2024-04-07
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/com/userPackagePromotion")
public class ComUserPackagePromotionController extends BaseController {

    private final IComUserPackagePromotionService comUserPackagePromotionService;

    /**
     * 查询优惠码列表
     */
    @SaCheckPermission("com:userPackagePromotion:list")
    @GetMapping("/list")
    public TableDataInfo<ComUserPackagePromotionVo> list(ComUserPackagePromotionBo bo, PageQuery pageQuery) {
        return comUserPackagePromotionService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出优惠码列表
     */
    @SaCheckPermission("com:userPackagePromotion:export")
    @Log(title = "优惠码", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ComUserPackagePromotionBo bo, HttpServletResponse response) {
        List<ComUserPackagePromotionVo> list = comUserPackagePromotionService.queryList(bo);
        ExcelUtil.exportExcel(list, "优惠码", ComUserPackagePromotionVo.class, response);
    }

    /**
     * 获取优惠码详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("com:userPackagePromotion:query")
    @GetMapping("/{id}")
    public R<ComUserPackagePromotionVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(comUserPackagePromotionService.queryById(id));
    }

    /**
     * 新增优惠码
     */
    @SaCheckPermission("com:userPackagePromotion:add")
    @Log(title = "优惠码", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ComUserPackagePromotionBo bo) {
        return toAjax(comUserPackagePromotionService.insertByBo(bo));
    }

    /**
     * 修改优惠码
     */
    @SaCheckPermission("com:userPackagePromotion:edit")
    @Log(title = "优惠码", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ComUserPackagePromotionBo bo) {
        return toAjax(comUserPackagePromotionService.updateByBo(bo));
    }

    /**
     * 删除优惠码
     *
     * @param ids 主键串
     */
    @SaCheckPermission("com:userPackagePromotion:remove")
    @Log(title = "优惠码", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(comUserPackagePromotionService.deleteWithValidByIds(List.of(ids), true));
    }
}
