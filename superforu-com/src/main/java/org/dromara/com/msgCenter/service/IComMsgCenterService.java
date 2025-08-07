package org.dromara.com.msgCenter.service;

import org.dromara.com.msgCenter.domain.ComMsgCenter;
import org.dromara.com.msgCenter.domain.vo.ComMsgCenterVo;
import org.dromara.com.msgCenter.domain.bo.ComMsgCenterBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 消息中心Service接口
 *
 * @author Lion Li
 * @date 2023-11-28
 */
public interface IComMsgCenterService {

    /**
     * 查询消息中心
     */
    ComMsgCenterVo queryById(Long id);

    /**
     * 查询消息中心列表
     */
    TableDataInfo<ComMsgCenterVo> queryPageList(ComMsgCenterBo bo, PageQuery pageQuery);

    /**
     * 查询消息中心列表
     */
    List<ComMsgCenterVo> queryList(ComMsgCenterBo bo);

    /**
     * 新增消息中心
     */
    Boolean insertByBo(ComMsgCenterBo bo);

    /**
     * 修改消息中心
     */
    Boolean updateByBo(ComMsgCenterBo bo);

    /**
     * 校验并批量删除消息中心信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
