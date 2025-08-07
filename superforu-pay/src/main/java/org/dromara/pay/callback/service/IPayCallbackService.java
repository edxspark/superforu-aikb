package org.dromara.pay.callback.service;

import org.dromara.pay.callback.domain.PayCallback;
import org.dromara.pay.callback.domain.vo.PayCallbackVo;
import org.dromara.pay.callback.domain.bo.PayCallbackBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 支付回调Service接口
 *
 * @author Lion Li
 * @date 2024-03-22
 */
public interface IPayCallbackService {

    /**
     * 查询支付回调
     */
    PayCallbackVo queryById(Long id);

    /**
     * 查询支付回调列表
     */
    TableDataInfo<PayCallbackVo> queryPageList(PayCallbackBo bo, PageQuery pageQuery);

    /**
     * 查询支付回调列表
     */
    List<PayCallbackVo> queryList(PayCallbackBo bo);

    /**
     * 新增支付回调
     */
    Boolean insertByBo(PayCallbackBo bo);

    /**
     * 修改支付回调
     */
    Boolean updateByBo(PayCallbackBo bo);

    /**
     * 校验并批量删除支付回调信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
