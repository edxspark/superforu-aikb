package org.dromara.com.user.service;

import org.dromara.com.user.domain.vo.ComUserVo;
import org.dromara.com.user.domain.bo.ComUserBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.List;

/**
 * 用户信息Service接口
 *
 * @author Lion Li
 * @date 2023-11-28
 */
public interface IComUserService {

    /**
     * 查询用户信息
     */
    ComUserVo queryUserInfo(Long userId);

    /**
     * 查询用户信息
     */
    ComUserVo queryUserInfoByUserAccount(Long userId);

    /**
     * 修改用户信息
     */
    Boolean updateByBo(ComUserBo bo);

    /**
     * 根据用户id集合查询信息列表
     */
    List<ComUserVo> queryListByUserIds(List<Long> userIds);

    /**
     * 获取未在团队的用户
     */
    TableDataInfo<ComUserVo> queryUserInfoNotInTeamPage(List<Long> userIds,String userAccount, PageQuery pageQuery);
}
