package org.dromara.com.teamMate.service;

import org.dromara.com.teamMate.domain.ComTeamMate;
import org.dromara.com.teamMate.domain.bo.ComTeamMateBo;
import org.dromara.com.teamMate.domain.bo.ComTeamMateDeleteBo;
import org.dromara.com.teamMate.domain.bo.ComTeamMateQueryListBo;
import org.dromara.com.teamMate.domain.vo.ComTeamMateVo;
import org.dromara.com.teamMate.domain.bo.ComTeamMateEditBo;
import org.dromara.com.teamMate.domain.bo.ComUserNotInTeamQueryPageBo;
import org.dromara.com.teamMate.domain.vo.ComUserNotInTeamInfoVo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.List;

/**
 * 团队成员管理Service接口
 *
 * @author JackLiao
 * @date 2023-12-08
 */
public interface IComTeamMateService {


    /**
     * 根据用户id查询所有团队
     */
    List<ComTeamMateVo> queryTeamIdsByUserId(Long userId);

    /**
     * 查询团队成员管理列表
     */
    TableDataInfo<ComTeamMateVo> queryPageList(ComTeamMateQueryListBo bo, PageQuery pageQuery);

    /**
     * 新增团队时添加自己为管理员
     */
    Boolean insertByBo(ComTeamMateBo bo);

    /**
     * 邀请用户加入团队
     */
    Boolean insertByInviteBo(ComTeamMateBo bo);

    /**
     * 修改团队成员管理
     */
    Boolean updateByBo(ComTeamMateEditBo bo);

    /**
     * 根据团队id批量删除团队成员
     */
    void deleteTeamMateByTeamId(Long teamId);

    /**
     * 团队成员退出团队
     */
    void teamMateQuitTeam(Long teamMateId);

    /**
     * 删除团队成员
     */
    void removeTeamMate(ComTeamMateDeleteBo teamMateId);

    /**
     * 根据团队id和用户id查询团队成员信息
     */
    ComTeamMate queryTeamMateDetail(Long teamId);

    /**
     * 查询用户管理的团队
     */
    List<ComTeamMateVo> queryUserOwnerTeam();

    /**
     * 获取未在团队的用户
     */
    TableDataInfo<ComUserNotInTeamInfoVo> queryUserInfoNotInTeamPage(ComUserNotInTeamQueryPageBo bo, PageQuery pageQuery);

}
