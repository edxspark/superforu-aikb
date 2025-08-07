package org.dromara.com.teamMate.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.dtflys.forest.annotation.Query;
import lombok.RequiredArgsConstructor;
import org.dromara.com.teamMate.domain.bo.ComTeamMateBo;
import org.dromara.com.teamMate.domain.bo.ComTeamMateDeleteBo;
import org.dromara.com.teamMate.domain.bo.ComTeamMateQueryListBo;
import org.dromara.com.teamMate.domain.bo.ComUserNotInTeamQueryPageBo;
import org.dromara.com.teamMate.domain.vo.ComUserNotInTeamInfoVo;
import org.dromara.com.user.domain.vo.ComUserVo;
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
import org.dromara.com.teamMate.domain.vo.ComTeamMateVo;
import org.dromara.com.teamMate.domain.bo.ComTeamMateEditBo;
import org.dromara.com.teamMate.service.IComTeamMateService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 团队成员管理
 *
 * @author JackLiao
 * @date 2023-12-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/com/teamMate")
public class ComTeamMateController extends BaseController {

    private final IComTeamMateService comTeamMateService;

    /**
     * 查询团队成员管理列表
     */
    @SaCheckLogin
    @GetMapping("/pageList")
    public TableDataInfo<ComTeamMateVo> queryPageList(@Validated(Query.class) ComTeamMateQueryListBo bo, PageQuery pageQuery) {
        return comTeamMateService.queryPageList(bo,pageQuery);
    }



    /**
     * 邀请用户加入团队
     */
    @SaCheckLogin
    @Log(title = "团队成员管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/add")
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ComTeamMateBo bo) {
        comTeamMateService.insertByInviteBo(bo);
        return R.ok();
    }

    /**
     * 修改团队成员管理
     */
    @SaCheckLogin
    @Log(title = "团队成员管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PostMapping("edit")
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ComTeamMateEditBo bo) {
        return toAjax(comTeamMateService.updateByBo(bo));
    }

    /**
     * 删除团队成员管理
     */
    @SaCheckLogin
    @Log(title = "团队成员管理", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public R<Void> remove(@Validated(EditGroup.class) @RequestBody ComTeamMateDeleteBo bo) {
        comTeamMateService.removeTeamMate(bo);
        return R.ok();
    }


    /**
     * 获取未在团队的用户
     *
     */
    @SaCheckLogin
    @GetMapping("/getUserInfoNotInTeam")
    public TableDataInfo<ComUserNotInTeamInfoVo> queryUserInfoNotInTeamPage(@Validated(Query.class) ComUserNotInTeamQueryPageBo bo, PageQuery pageQuery) {
        return comTeamMateService.queryUserInfoNotInTeamPage(bo,pageQuery);
    }
}
