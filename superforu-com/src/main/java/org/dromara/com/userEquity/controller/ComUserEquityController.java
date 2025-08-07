package org.dromara.com.userEquity.controller;

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
import org.dromara.com.userEquity.domain.vo.ComUserEquityVo;
import org.dromara.com.userEquity.domain.bo.ComUserEquityBo;
import org.dromara.com.userEquity.service.IComUserEquityService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 用户权益套餐配置
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/com/userEquity")
public class ComUserEquityController extends BaseController {

    private final IComUserEquityService comUserEquityService;

    /**
     * 查询用户权益套餐配置列表
     */
    @SaCheckPermission("com:userEquity:list")
    @GetMapping("/list")
    public TableDataInfo<ComUserEquityVo> list(ComUserEquityBo bo, PageQuery pageQuery) {
        return comUserEquityService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出用户权益套餐配置列表
     */
    @SaCheckPermission("com:userEquity:export")
    @Log(title = "用户权益套餐配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ComUserEquityBo bo, HttpServletResponse response) {
        List<ComUserEquityVo> list = comUserEquityService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户权益套餐配置", ComUserEquityVo.class, response);
    }

    /**
     * 获取用户权益套餐配置详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("com:userEquity:query")
    @GetMapping("/{id}")
    public R<ComUserEquityVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(comUserEquityService.queryById(id));
    }

    /**
     * 新增用户权益套餐配置
     */
    @SaCheckPermission("com:userEquity:add")
    @Log(title = "用户权益套餐配置", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ComUserEquityBo bo) {
        return toAjax(comUserEquityService.insertByBo(bo));
    }

    /**
     * 修改用户权益套餐配置
     */
    @SaCheckPermission("com:userEquity:edit")
    @Log(title = "用户权益套餐配置", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ComUserEquityBo bo) {
        return toAjax(comUserEquityService.updateByBo(bo));
    }

    /**
     * 删除用户权益套餐配置
     *
     * @param ids 主键串
     */
    @SaCheckPermission("com:userEquity:remove")
    @Log(title = "用户权益套餐配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(comUserEquityService.deleteWithValidByIds(List.of(ids), true));
    }
}
