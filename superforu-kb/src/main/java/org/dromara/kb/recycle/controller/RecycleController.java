package org.dromara.kb.recycle.controller;

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
import org.dromara.kb.recycle.domain.vo.RecycleVo;
import org.dromara.kb.recycle.domain.bo.RecycleBo;
import org.dromara.kb.recycle.service.IRecycleService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 回收站
 *
 * @author Lion Li
 * @date 2023-11-28
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/kb/recycle")
public class RecycleController extends BaseController {

    private final IRecycleService recycleService;

    /**
     * 查询回收站列表
     */
    @SaCheckLogin
    @GetMapping("/list")
    public TableDataInfo<RecycleVo> list(RecycleBo bo, PageQuery pageQuery) {
        return recycleService.queryPageList(bo, pageQuery);
    }


    /**
     * 获取回收站详细信息
     *
     * @param id 主键
     */
    @SaCheckLogin
    @GetMapping("/{id}")
    public R<RecycleVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(recycleService.queryById(id));
    }

    /**
     * 新增回收站
     */
    @SaCheckLogin
    @Log(title = "回收站", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody RecycleBo bo) {
        return toAjax(recycleService.insertByBo(bo));
    }

    /**
     * 修改回收站
     */
    @SaCheckLogin
    @Log(title = "回收站", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody RecycleBo bo) {
        return toAjax(recycleService.updateByBo(bo));
    }

    /**
     * 恢复
     */
    @SaCheckLogin
    @Log(title = "恢复", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/recover")
    public R<Void> recover(@RequestParam("id") Long id) {
        return toAjax(recycleService.recover(id));
    }

    /**
     * 删除回收站
     *
     * @param ids 主键串
     */
    @SaCheckLogin
    @Log(title = "回收站", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(recycleService.deleteWithValidByIds(List.of(ids), true));
    }
}
