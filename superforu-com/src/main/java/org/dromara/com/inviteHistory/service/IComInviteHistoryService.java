package org.dromara.com.inviteHistory.service;

import org.dromara.com.inviteHistory.domain.ComInviteHistory;
import org.dromara.com.inviteHistory.domain.vo.ComInviteHistoryVo;
import org.dromara.com.inviteHistory.domain.bo.ComInviteHistoryBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 邀请记录Service接口
 *
 * @author Lion Li
 * @date 2023-11-28
 */
public interface IComInviteHistoryService {

    /**
     * 查询邀请记录
     */
    ComInviteHistoryVo queryById(Long id);

    /**
     * 查询邀请记录列表
     */
    TableDataInfo<ComInviteHistoryVo> queryPageList(ComInviteHistoryBo bo, PageQuery pageQuery);

    /**
     * 查询邀请记录列表
     */
    List<ComInviteHistoryVo> queryList(ComInviteHistoryBo bo);

    /**
     * 新增邀请记录
     */
    Boolean insertByBo(ComInviteHistoryBo bo);

    /**
     * 修改邀请记录
     */
    Boolean updateByBo(ComInviteHistoryBo bo);

    /**
     * 校验并批量删除邀请记录信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
