package org.dromara.com.teamMate.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.dromara.com.team.domain.ComTeam;
import org.dromara.com.teamMate.domain.bo.ComTeamMateBo;
import org.dromara.com.teamMate.domain.bo.ComTeamMateDeleteBo;
import org.dromara.com.teamMate.domain.bo.ComTeamMateQueryListBo;
import org.dromara.com.teamMate.domain.bo.ComUserNotInTeamQueryPageBo;
import org.dromara.com.teamMate.domain.vo.ComUserNotInTeamInfoVo;
import org.dromara.com.utils.common.ErrorMsg;
import org.dromara.com.utils.enums.ConfigTypeEnum;
import org.dromara.com.utils.enums.DelFlagEnum;
import org.dromara.com.utils.enums.RoleTypes;
import org.dromara.com.user.domain.vo.ComUserVo;
import org.dromara.com.user.service.IComUserService;
import org.dromara.com.utils.loginUtil.LoginUtil;
import org.dromara.common.core.exception.base.BaseException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.ValidatorUtils;
import org.dromara.common.core.validate.auth.PasswordGroup;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.com.teamMate.domain.bo.ComTeamMateEditBo;
import org.dromara.com.teamMate.domain.vo.ComTeamMateVo;
import org.dromara.com.teamMate.domain.ComTeamMate;
import org.dromara.com.teamMate.mapper.ComTeamMateMapper;
import org.dromara.com.teamMate.service.IComTeamMateService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 团队成员管理Service业务层处理
 *
 * @author JackLiao
 * @date 2023-12-08
 */
@RequiredArgsConstructor
@Service
public class ComTeamMateServiceImpl implements IComTeamMateService {

    private final IComUserService comUserService;

    private final ComTeamMateMapper baseMapper;

    // 将 Map 定义为一个静态字段，表示全局的配置类型映射
    private static final Map<Integer, String> roleTypeMap = new HashMap<>();

    static {
        // 在静态块中初始化 Map，将枚举值映射到 Map 中
        for (RoleTypes type : RoleTypes.values()) {
            roleTypeMap.put(type.getValue(), type.getDescription());
        }
    }

    /**
     * 根据用户id查询所有团队
     */
    @Override
    public List<ComTeamMateVo> queryTeamIdsByUserId(Long userId) {
        return baseMapper.selectVoList(
            new LambdaQueryWrapper<ComTeamMate>()
                .eq(ComTeamMate::getLinkUserId, userId));
    }

    /**
     * 查询团队成员管理列表
     * 1. 获取团队成员列表
     * 2. 填充成员信息
     */
    @Override
    public TableDataInfo<ComTeamMateVo> queryPageList(ComTeamMateQueryListBo bo, PageQuery pageQuery) {

        // 1. 获取团队成员列表
        LambdaQueryWrapper<ComTeamMate> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ComTeamMate::getLinkTeamId, bo.getTeamId());
        Page<ComTeamMateVo> result  = baseMapper.selectVoPage(pageQuery.build(), lqw);
        List<ComTeamMateVo> records = result.getRecords();

        // 2. 填充成员信息
        List<Long> userIds = records.stream().map(ComTeamMateVo::getLinkUserId).toList();
        List<ComUserVo> comUserVos = comUserService.queryListByUserIds(userIds);
        Map<Long, ComUserVo> comUserVoMap = comUserVos.stream()
            .collect(Collectors.toMap(ComUserVo::getId, Function.identity()));

        records.forEach(teamMateVo -> {
            ComUserVo comUserVo = comUserVoMap.get(teamMateVo.getLinkUserId());
            teamMateVo.setUserAccount(comUserVo.getUserAccount());
            teamMateVo.setUserName(comUserVo.getUserName());
        });

        return TableDataInfo.build(result);
    }


    /**
     * 新增团队是添加自己为管理员
     */
    @Override
    public Boolean insertByBo(ComTeamMateBo bo) {
        ComTeamMate add = MapstructUtils.convert(bo, ComTeamMate.class);
        add.setRoleType(RoleTypes.ADMIN.getValue());
        boolean flag = baseMapper.insert(add) > 0;
        return flag;
    }

    /**
     * 邀请用户加入团队
     * 1. 检查角色（不能是管理员）
     * 2. 检查是否存在成员
     * 3. 检查是否成员已存在团队
     * 4. 新增团队成员
     */
    @Override
    public Boolean insertByInviteBo(ComTeamMateBo bo) {

        // 1. 检查角色（不能是管理员）
        if(!roleTypeMap.containsKey(bo.getRoleType()) || RoleTypes.ADMIN.getValue().equals(bo.getRoleType())){
            throw new BaseException(ErrorMsg.ERR_COM_ROLE_MISMATCH.getMessage());
        }

        // 2. 检查是否存在成员
        ComUserVo comUserVo = comUserService.queryUserInfoByUserAccount(bo.getLinkUserId());
        if (ObjectUtil.isNull(comUserVo)) {
            throw new BaseException(ErrorMsg.ERR_COM_USER_NOT_EXIST.getMessage());
        }else{
            // 设置用户主键ID
            bo.setLinkUserId(comUserVo.getId());
        }

        // 3. 检查是否成员已存在团队
        ComTeamMate comTeamMate = baseMapper.selectOne(new LambdaQueryWrapper<ComTeamMate>()
            .eq(ComTeamMate::getLinkTeamId, bo.getLinkTeamId())
            .eq(ComTeamMate::getLinkUserId, bo.getLinkUserId()));
        if (ObjectUtil.isNotNull(comTeamMate)) {
            throw new BaseException(ErrorMsg.ERR_COM_TEAM_MATE_EXIST.getMessage());
        }

        // 4. 新增团队成员
        ComTeamMate add = MapstructUtils.convert(bo, ComTeamMate.class);
        boolean flag = baseMapper.insert(add) > 0;
        return flag;
    }

    /**
     * 修改团队成员管理
     */
    @Override
    public Boolean updateByBo(ComTeamMateEditBo bo) {
        ComTeamMate update = MapstructUtils.convert(bo, ComTeamMate.class);

        ComTeamMate comTeamMate = queryTeamMateDetail(bo.getTeamId());

        if (!RoleTypes.ADMIN.getValue().equals(comTeamMate.getRoleType())) {
            throw new BaseException(ErrorMsg.ERR_COM_ROLE_MISMATCH.getMessage());
        }
        if (comTeamMate.getId().equals(bo.getId())) {
            throw new BaseException(ErrorMsg.ERR_COM_ROLE_MISMATCH.getMessage());
        }
        if (RoleTypes.ADMIN.getValue().equals(bo.getRoleType())) {
            throw new BaseException(ErrorMsg.ERR_COM_ROLE_MISMATCH.getMessage());
        }
        return baseMapper.updateById(update) > 0;
    }


    /**
     * 根据团队id批量删除团队成员
     *
     * @param teamId
     */
    @Override
    public void deleteTeamMateByTeamId(Long teamId) {
        LambdaUpdateWrapper<ComTeamMate> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ComTeamMate::getLinkTeamId, teamId);
        updateWrapper.set(ComTeamMate::getUpdateBy, LoginUtil.getUserId());
        updateWrapper.set(ComTeamMate::getDelFlag, DelFlagEnum.DELETED.getValue());
        updateWrapper.set(ComTeamMate::getUpdateTime, LocalDateTime.now());
        baseMapper.update(updateWrapper);
    }


    /**
     * 团队成员退出团队
     *
     * @param teamMateId
     */
    @Override
    public void teamMateQuitTeam(Long teamMateId) {
        ComTeamMate comTeamMate = new ComTeamMate();
        comTeamMate.setId(teamMateId);
        comTeamMate.setUpdateBy(LoginUtil.getUserId());
        baseMapper.deleteById(comTeamMate);
    }

    /**
     * 删除组员
     * @param bo
     */
    @Override
    public void removeTeamMate(ComTeamMateDeleteBo bo) {
        ComTeamMate comTeamMate = queryTeamMateDetail(bo.getTeamId());

        if (!RoleTypes.ADMIN.getValue().equals(comTeamMate.getRoleType())) {
            throw new BaseException(ErrorMsg.ERR_COM_ROLE_MISMATCH.getMessage());
        }
        if (comTeamMate.getId().equals(bo.getTeamMateId())) {
            throw new BaseException(ErrorMsg.ERR_COM_ROLE_MISMATCH.getMessage());
        }
        //删除团队成员
        baseMapper.deleteById(bo.getTeamMateId());
    }

    /**
     * 根据团队id和用户id查询团队成员信息
     */
    @Override
    public ComTeamMate queryTeamMateDetail(Long teamId) {
        //查询团队成员信息
        Long userId = LoginUtil.getUserId();
        ComTeamMate comTeamMate = baseMapper.selectOne(new LambdaQueryWrapper<ComTeamMate>()
            .eq(ComTeamMate::getLinkTeamId, teamId)
            .eq(ComTeamMate::getLinkUserId, userId));
        if (ObjectUtil.isNull(comTeamMate)) {
            throw new BaseException(ErrorMsg.ERR_COM_TEAM_MATE_NOT_EXIST.getMessage());
        }
        return comTeamMate;
    }

    /**
     * 查询用户管理的团队
     *
     * @return
     */
    @Override
    public List<ComTeamMateVo> queryUserOwnerTeam() {
        Long userId = LoginUtil.getUserId();
        List<ComTeamMateVo> comTeamMateVos = baseMapper.selectVoList(new LambdaQueryWrapper<ComTeamMate>()
            .eq(ComTeamMate::getRoleType, RoleTypes.ADMIN.getValue())
            .eq(ComTeamMate::getLinkUserId, userId));
        return comTeamMateVos;
    }


    /**
     * 获取未在团队的用户
     * 1.查询该团队所有成员用户
     * 2.查询所有用户未在该团队的
     * 3.组合数据
     * @param bo
     * @param pageQuery
     * @return
     */
    @Override
    public TableDataInfo<ComUserNotInTeamInfoVo> queryUserInfoNotInTeamPage(ComUserNotInTeamQueryPageBo bo, PageQuery pageQuery) {

        ValidatorUtils.validate(bo, Query.class);

        //查询该团队所有成员用户
        List<ComTeamMateVo> comTeamMateVos = baseMapper.selectVoList(new LambdaQueryWrapper<ComTeamMate>()
            .eq(ComTeamMate::getLinkTeamId, bo.getTeamId()));

        List<Long> collect = comTeamMateVos.stream().map(ComTeamMateVo::getLinkUserId).toList();

        //查询所有用户未在该团队的
        TableDataInfo<ComUserVo> comUserVoTableDataInfo = comUserService.queryUserInfoNotInTeamPage(collect,
            bo.getUserAccount(),
            pageQuery);
        List<ComUserVo> rows = comUserVoTableDataInfo.getRows();

        // 将 List<ComUserVo> 转换为 List<ComUserNotInTeamInfoVo>
        List<ComUserNotInTeamInfoVo> voList = rows.stream()
            .map(source -> {
                ComUserNotInTeamInfoVo destination = new ComUserNotInTeamInfoVo();
                destination.setUserAccount(source.getUserAccount());
                destination.setUserName(source.getUserName());
                destination.setId(source.getId());
                return destination;
            })
            .collect(Collectors.toList());
        TableDataInfo<ComUserNotInTeamInfoVo> resultPageData = new TableDataInfo<>();
        resultPageData.setCode(comUserVoTableDataInfo.getCode());
        resultPageData.setMsg(comUserVoTableDataInfo.getMsg());
        resultPageData.setTotal(comUserVoTableDataInfo.getTotal());
        resultPageData.setRows(voList);
        return resultPageData;
    }
}
