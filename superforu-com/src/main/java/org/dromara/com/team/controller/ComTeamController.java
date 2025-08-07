package org.dromara.com.team.controller;

import java.util.List;

import cn.dev33.satoken.annotation.SaCheckLogin;
import lombok.RequiredArgsConstructor;
import org.dromara.com.team.domain.bo.ComTeamDeleteBo;
import org.dromara.com.team.domain.bo.ComTeamEditBo;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.com.team.domain.vo.ComTeamVo;
import org.dromara.com.team.domain.bo.ComTeamBo;
import org.dromara.com.team.service.IComTeamService;

/**
 * 团队管理
 *
 * @author JackLiao
 * @date 2023-12-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/com/team")
public class ComTeamController extends BaseController {

    private final IComTeamService comTeamService;

    /**
     * 查询团队管理列表
     */
    @SaCheckLogin
    @GetMapping("/list")
    public TableDataInfo<ComTeamVo> list(PageQuery pageQuery) {
        return comTeamService.queryList(pageQuery);
    }


    /**
     * 新增团队管理
     */
    @SaCheckLogin
    @Log(title = "团队管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ComTeamBo bo) {
        return toAjax(comTeamService.insertByBo(bo));
    }

    /**
     * 修改团队管理
     */
    @SaCheckLogin
    @Log(title = "团队管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("/edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ComTeamEditBo bo) {
        return toAjax(comTeamService.updateByBo(bo));
    }

    /**
     * 删除团队管理(解散团队)
     */
    @SaCheckLogin
    @Log(title = "团队管理", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public R<Void> remove(@Validated(EditGroup.class) @RequestBody ComTeamDeleteBo bo) {
        comTeamService.deleteTeam(bo);
        return R.ok();
    }

    /**
     * 退出团队
     */
    @SaCheckLogin
    @Log(title = "团队管理", businessType = BusinessType.DELETE)
    @PostMapping("/quit")
    public R<Void> quit(@Validated(EditGroup.class) @RequestBody ComTeamDeleteBo bo) {
        comTeamService.quitTeam(bo);
        return R.ok();
    }

}
