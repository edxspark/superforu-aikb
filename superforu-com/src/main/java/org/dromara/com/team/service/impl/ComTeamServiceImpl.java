package org.dromara.com.team.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.dromara.com.team.domain.bo.ComTeamDeleteBo;
import org.dromara.com.team.domain.bo.ComTeamEditBo;
import org.dromara.com.teamMate.domain.ComTeamMate;
import org.dromara.com.teamMate.domain.bo.ComTeamMateBo;
import org.dromara.com.teamMate.domain.vo.ComTeamMateVo;
import org.dromara.com.utils.common.ErrorMsg;
import org.dromara.com.utils.enums.RoleTypes;
import org.dromara.com.teamMate.service.IComTeamMateService;
import org.dromara.com.utils.loginUtil.LoginUtil;
import org.dromara.common.core.exception.base.BaseException;
import org.dromara.common.core.utils.MapstructUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.system.service.impl.SysConfigServiceImpl;
import org.springframework.stereotype.Service;
import org.dromara.com.team.domain.bo.ComTeamBo;
import org.dromara.com.team.domain.vo.ComTeamVo;
import org.dromara.com.team.domain.ComTeam;
import org.dromara.com.team.mapper.ComTeamMapper;
import org.dromara.com.team.service.IComTeamService;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 团队管理Service业务层处理
 *
 * @author JackLiao
 * @date 2023-12-08
 */
@RequiredArgsConstructor
@Service
public class ComTeamServiceImpl implements IComTeamService {

    private final IComTeamMateService comTeamMateService;

    private final ComTeamMapper baseMapper;


    /**
     * 查询团队管理列表
     */
    @Override
    public TableDataInfo<ComTeamVo> queryList(PageQuery pageQuery) {
        Long userId = LoginUtil.getUserId();
        //查询用户所属的团队
        List<ComTeamMateVo> comTeamMateVos = comTeamMateService.queryTeamIdsByUserId(userId);
        if (CollUtil.isEmpty(comTeamMateVos)) {
            return TableDataInfo.build();
        }
        List<Long> teamIds = comTeamMateVos.stream().map(ComTeamMateVo::getLinkTeamId).toList();

        //查询团队信息
        LambdaQueryWrapper<ComTeam> lqw = new LambdaQueryWrapper<>();
        lqw.in(ComTeam::getId, teamIds);
        Page<ComTeamVo> comTeamVoIPage = baseMapper.selectVoPage(pageQuery.build(), lqw);
        List<ComTeamVo> records = comTeamVoIPage.getRecords();

        // 使用 Stream 流对 团队成员权限 进行处理
        Map<Long, Integer> roletypeMap = comTeamMateVos.stream()
            .collect(Collectors.toMap(ComTeamMateVo::getLinkTeamId, ComTeamMateVo::getRoleType));

        // 遍历 团队，根据 teamId 将 roletype 数据添加到 团队 中相应的元素中
        records.forEach(team -> {
            Integer roleType = roletypeMap.getOrDefault(team.getId(), 0); // 默认值为0，可根据实际情况调整
            team.setRoleType(roleType);
        });
        Collections.sort(records, Comparator.comparingInt(ComTeamVo::getRoleType).reversed());
        return TableDataInfo.build(records);
    }

    /**
     * 新增团队及添加自己为团队管理员
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertByBo(ComTeamBo bo) {

        List<ComTeamMateVo> comTeamMateVos = comTeamMateService.queryUserOwnerTeam();
        if (CollUtil.isNotEmpty(comTeamMateVos)) {
            List<Long> collect = comTeamMateVos.stream().map(ComTeamMateVo::getLinkTeamId).collect(Collectors.toList());
            boolean exists = baseMapper.exists(new LambdaQueryWrapper<ComTeam>()
                .eq(ComTeam::getTeamName, bo.getTeamName())
                .in(ComTeam::getId, collect));
            if (exists) {
                throw new BaseException(ErrorMsg.ERR_COM_TEAM_NAME_EXIST.getMessage());
            }
        }
        ComTeam comTeam = MapstructUtils.convert(bo, ComTeam.class);
        boolean flag = baseMapper.insert(comTeam) > 0;
        if (flag) {
            Long id = comTeam.getId();
            ComTeamMateBo comTeamMateBo = new ComTeamMateBo();
            comTeamMateBo.setLinkTeamId(id);
            comTeamMateBo.setLinkUserId(LoginUtil.getUserId());
            comTeamMateService.insertByBo(comTeamMateBo);
        }
        return flag;
    }

    /**
     * 修改团队管理
     * 1.查询该用户管理的所有团队
     * 2.去除所修改团队id再次查询团队名称是否重复
     * 3.更新团队
     */
    @Override
    public Boolean updateByBo(ComTeamEditBo bo) {
        ComTeam update = MapstructUtils.convert(bo, ComTeam.class);
        validEntityBeforeSave(update);

        //校验名称是否重复
        //查询该用户管理的所有团队
        List<ComTeamMateVo> comTeamMateVos = comTeamMateService.queryUserOwnerTeam();
        if (CollUtil.isEmpty(comTeamMateVos)) {
            throw new BaseException(ErrorMsg.ERR_COM_UPDATE_ERR.getMessage());
        }

        List<Long> collect = comTeamMateVos.stream().map(ComTeamMateVo::getLinkTeamId).collect(Collectors.toList());
        collect.remove(bo.getId());
        if (CollUtil.isEmpty(collect)) {
            return baseMapper.updateById(update) > 0;
        }

        boolean exists = baseMapper.exists(new LambdaQueryWrapper<ComTeam>()
            .eq(ComTeam::getTeamName, bo.getTeamName())
            .in(ComTeam::getId, collect));
        if (exists) {
            throw new BaseException(ErrorMsg.ERR_COM_TEAM_NAME_EXIST.getMessage());
        }
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 管理员保存前的数据校验
     */
    private void validEntityBeforeSave(ComTeam entity) {
        //查询当前用户权限是否为管理员
        ComTeamMate comTeamMate = comTeamMateService.queryTeamMateDetail(entity.getId());

        if (ObjectUtil.isNull(comTeamMate)) {
            throw new BaseException(ErrorMsg.ERR_COM_TEAM_MATE_NOT_EXIST.getMessage());
        }
        Integer roleType = comTeamMate.getRoleType();
        if (!RoleTypes.ADMIN.getValue().equals(roleType)) {
            throw new BaseException(ErrorMsg.ERR_COM_ROLE_MISMATCH.getMessage());
        }
    }

    /**
     * 删除团队管理(解散团队)
     */
    @Override
    public void deleteTeam(ComTeamDeleteBo bo) {
        //1.删除团队
        ComTeam update = MapstructUtils.convert(bo, ComTeam.class);
        validEntityBeforeSave(update);
        baseMapper.deleteById(update);

        //2.删除团队成员
        //根据团队id批量删除团队成员
        comTeamMateService.deleteTeamMateByTeamId(bo.getId());
    }

    /**
     * 退出团队
     */
    @Override
    public void quitTeam(ComTeamDeleteBo bo) {
        Long userId = LoginUtil.getUserId();
        ComTeamMateBo comTeamMateBo = new ComTeamMateBo();
        comTeamMateBo.setLinkTeamId(bo.getId());
        comTeamMateBo.setLinkUserId(userId);
        ComTeamMate comTeamMate = comTeamMateService.queryTeamMateDetail(bo.getId());
        if (ObjectUtil.isNull(comTeamMate)) {
            throw new BaseException(ErrorMsg.ERR_COM_TEAM_MATE_NOT_EXIST.getMessage());
        }
        Integer roleType = comTeamMate.getRoleType();
        if (RoleTypes.ADMIN.getValue().equals(roleType)) {
            throw new BaseException(ErrorMsg.ERR_COM_ROLE_MISMATCH.getMessage());
        }
        //退出团队
        //团队成员退出团队
        comTeamMateService.teamMateQuitTeam(comTeamMate.getId());
    }

}
