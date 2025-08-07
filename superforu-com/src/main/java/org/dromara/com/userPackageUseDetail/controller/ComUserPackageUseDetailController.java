package org.dromara.com.userPackageUseDetail.controller;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckLogin;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.com.userPackageUseDetail.domain.bo.AddUseDetailBo;
import org.dromara.com.userPackageUseDetail.domain.vo.ComAddUserPackageUseDetailVo;
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
import org.dromara.com.userPackageUseDetail.domain.vo.ComUserPackageUseDetailVo;
import org.dromara.com.userPackageUseDetail.domain.bo.ComUserPackageUseDetailBo;
import org.dromara.com.userPackageUseDetail.service.IComUserPackageUseDetailService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 用户充值消费明细
 *
 * @author Lion Li
 * @date 2024-03-21
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/com/userPackageUseDetail")
public class ComUserPackageUseDetailController extends BaseController {

    private final IComUserPackageUseDetailService comUserPackageUseDetailService;

    /**
     * 查询用户充值消费明细列表
     */
    @SaCheckPermission("com:userPackageUseDetail:list")
    @GetMapping("/list")
    public TableDataInfo<ComUserPackageUseDetailVo> list(ComUserPackageUseDetailBo bo, PageQuery pageQuery) {
        return comUserPackageUseDetailService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出用户充值消费明细列表
     */
    @SaCheckPermission("com:userPackageUseDetail:export")
    @Log(title = "用户充值消费明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ComUserPackageUseDetailBo bo, HttpServletResponse response) {
        List<ComUserPackageUseDetailVo> list = comUserPackageUseDetailService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户充值消费明细", ComUserPackageUseDetailVo.class, response);
    }

    /**
     * 获取用户充值消费明细详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("com:userPackageUseDetail:query")
    @GetMapping("/{id}")
    public R<ComUserPackageUseDetailVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(comUserPackageUseDetailService.queryById(id));
    }

    /**
     * 新增用户充值消费明细
     */
    @SaCheckPermission("com:userPackageUseDetail:add")
    @Log(title = "用户充值消费明细", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ComUserPackageUseDetailBo bo) {
        return toAjax(comUserPackageUseDetailService.insertByBo(bo));
    }

    /**
     * 新增用户充值消费明细(支持数字记数消费)
     */
    @SaCheckLogin
    @Log(title = "用户充值消费明细", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/addUseDetail")
    public ComAddUserPackageUseDetailVo addUseDetail(@Validated(AddGroup.class) @RequestBody AddUseDetailBo bo) {
        return comUserPackageUseDetailService.addUseDetailByBo(bo);
    }

    /**
     * 修改用户充值消费明细
     */
    @SaCheckPermission("com:userPackageUseDetail:edit")
    @Log(title = "用户充值消费明细", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ComUserPackageUseDetailBo bo) {
        return toAjax(comUserPackageUseDetailService.updateByBo(bo));
    }

    /**
     * 删除用户充值消费明细
     *
     * @param ids 主键串
     */
    @SaCheckPermission("com:userPackageUseDetail:remove")
    @Log(title = "用户充值消费明细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(comUserPackageUseDetailService.deleteWithValidByIds(List.of(ids), true));
    }
}
