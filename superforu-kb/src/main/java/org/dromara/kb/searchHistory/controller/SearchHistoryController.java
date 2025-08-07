package org.dromara.kb.searchHistory.controller;

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
import org.dromara.kb.searchHistory.domain.vo.SearchHistoryVo;
import org.dromara.kb.searchHistory.domain.bo.SearchHistoryBo;
import org.dromara.kb.searchHistory.service.ISearchHistoryService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 搜索历史
 *
 * @author Moks
 * @date 2024-03-06
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/kb/searchHistory")
public class SearchHistoryController extends BaseController {

    private final ISearchHistoryService searchHistoryService;

    /**
     * 查询搜索历史列表
     */
    @SaCheckLogin
    @GetMapping("/list")
    public TableDataInfo<SearchHistoryVo> list(SearchHistoryBo bo, PageQuery pageQuery) {
        return searchHistoryService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出搜索历史列表
     */
    @SaCheckLogin
    @Log(title = "搜索历史", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SearchHistoryBo bo, HttpServletResponse response) {
        List<SearchHistoryVo> list = searchHistoryService.queryList(bo);
        ExcelUtil.exportExcel(list, "搜索历史", SearchHistoryVo.class, response);
    }

    /**
     * 获取搜索历史详细信息
     *
     * @param id 主键
     */
    @SaCheckLogin
    @GetMapping("/{id}")
    public R<SearchHistoryVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(searchHistoryService.queryById(id));
    }

    /**
     * 新增搜索历史
     */
    @SaCheckLogin
    @Log(title = "搜索历史", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SearchHistoryBo bo) {
        return toAjax(searchHistoryService.insertByBo(bo));
    }

    /**
     * 修改搜索历史
     */
    @SaCheckLogin
    @Log(title = "搜索历史", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SearchHistoryBo bo) {
        return toAjax(searchHistoryService.updateByBo(bo));
    }

    /**
     * 删除搜索历史
     *
     * @param ids 主键串
     */
    @SaCheckLogin
    @Log(title = "搜索历史", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(searchHistoryService.deleteWithValidByIds(List.of(ids), true));
    }
}
