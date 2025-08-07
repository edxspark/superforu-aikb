package org.dromara.com.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.dromara.com.userPackageDetail.domain.ComUserPackageDetail;
import org.dromara.com.userPackageDetail.domain.vo.ComUserPackageDetailVo;
import org.dromara.com.userPackageDetail.mapper.ComUserPackageDetailMapper;
import org.dromara.com.utils.common.ErrorMsg;
import org.dromara.com.utils.loginUtil.LoginUtil;
import org.dromara.common.core.exception.base.BaseException;
import org.dromara.common.core.utils.MapstructUtils;
import lombok.RequiredArgsConstructor;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.system.service.impl.SysConfigServiceImpl;
import org.springframework.stereotype.Service;
import org.dromara.com.user.domain.bo.ComUserBo;
import org.dromara.com.user.domain.vo.ComUserVo;
import org.dromara.com.user.domain.ComUser;
import org.dromara.com.user.mapper.ComUserMapper;
import org.dromara.com.user.service.IComUserService;

import java.util.List;


/**
 * 用户信息Service业务层处理
 *
 * @author JackLiao
 * @date 2023-11-28
 */
@RequiredArgsConstructor
@Service
public class ComUserServiceImpl implements IComUserService {


    private final ComUserMapper baseMapper;
    private final ComUserPackageDetailMapper comUserPackageDetailMapper;


    /**
     * 修改用户信息
     */
    @Override
    public Boolean updateByBo(ComUserBo bo) {
        Long userId = LoginUtil.getUserId();
        if (!userId.equals(bo.getId())) {
            throw new BaseException(ErrorMsg.ERR_COM_USER_ABNORMAL.getMessage());
        }
        ComUser update = MapstructUtils.convert(bo, ComUser.class);
        return baseMapper.updateById(update) > 0;
    }


    /**
     * 查询用户信息
     * 1. 获取基本信息
     * 2. 获取权益信息
     *
     * @param userId
     * @return
     */
    @Override
    public ComUserVo queryUserInfo(Long userId) {
        //  1. 获取基本信息
        ComUserVo comUserVo = baseMapper.selectVoById(userId);
        if (ObjectUtil.isNull(comUserVo)) {
            return null;
        }

        // 2.权益信息
        List<ComUserPackageDetailVo> userPackageDetailVos = comUserPackageDetailMapper.selectVoList(new LambdaQueryWrapper<ComUserPackageDetail>().eq(ComUserPackageDetail::getLinkUserId,userId));
        comUserVo.setUserPackageDetail(userPackageDetailVos);

        return comUserVo;

    }

    /**
     * 查询用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public ComUserVo queryUserInfoByUserAccount(Long userId) {
        ComUserVo comUserVo = baseMapper.selectVoOne(new LambdaQueryWrapper<ComUser>().in(ComUser::getUserAccount, userId));
        if (ObjectUtil.isNull(comUserVo)) {
            return null;
        }
        return comUserVo;

    }

    /**
     * 根据用户id集合查询信息列表
     *
     * @param userIds
     * @return
     */
    @Override
    public List<ComUserVo> queryListByUserIds(List<Long> userIds) {
        return baseMapper.selectVoList(new LambdaQueryWrapper<ComUser>().in(ComUser::getId, userIds));
    }

    @Override
    public TableDataInfo<ComUserVo> queryUserInfoNotInTeamPage(List<Long> userIds, String userAccount, PageQuery pageQuery) {
        IPage<ComUserVo> comUserVoIPage = baseMapper.selectVoPage(pageQuery.build(),
            new LambdaQueryWrapper<ComUser>()
                .select(ComUser::getId, ComUser::getUserAccount, ComUser::getUserName)
                .ne(ComUser::getId, userIds)
                .eq(ComUser::getUserAccount, userAccount));
        return TableDataInfo.build(comUserVoIPage);
    }


}

