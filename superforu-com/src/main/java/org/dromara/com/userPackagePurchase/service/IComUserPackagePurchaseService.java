package org.dromara.com.userPackagePurchase.service;

import org.dromara.com.userPackagePurchase.domain.ComUserPackagePurchase;
import org.dromara.com.userPackagePurchase.domain.bo.ComUserPackagePurchaseItemBo;
import org.dromara.com.userPackagePurchase.domain.vo.ComUserPackagePurchaseVo;
import org.dromara.com.userPackagePurchase.domain.bo.ComUserPackagePurchaseBo;
import org.dromara.common.core.domain.R;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.pay.callback.domain.bo.PayCallbackBo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 用户套餐购买详细Service接口
 *
 * @author Lion Li
 * @date 2024-03-21
 */
public interface IComUserPackagePurchaseService {

    /**
     * 赠送会员
     */
    Boolean giftToMember(Long userId,int monthCount);

    /**
     * 查询用户套餐购买详细
     */
    Boolean queryOrderStatus(String orderNo);


    /**
     * 订单付款成功
     * */
    Boolean paySuccess(String payWay, Map<String,String> params);

    /**
     * 查询用户套餐购买详细
     */
    ComUserPackagePurchaseVo queryById(Long id);

    /**
     * 查询用户套餐购买详细列表
     */
    TableDataInfo<ComUserPackagePurchaseVo> queryPageList(ComUserPackagePurchaseBo bo, PageQuery pageQuery);

    /**
     * 查询用户套餐购买详细列表
     */
    List<ComUserPackagePurchaseVo> queryList(ComUserPackagePurchaseBo bo);

    /**
     * 新增用户套餐购买详细
     */
    Boolean insertByBo(ComUserPackagePurchaseBo bo);

    /**
     * 用户套餐购买详细
     */
    R<String> purchaseItem(ComUserPackagePurchaseItemBo bo);

    /**
     * 修改用户套餐购买详细
     */
    Boolean updateByBo(ComUserPackagePurchaseBo bo);

    /**
     * 校验并批量删除用户套餐购买详细信息
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
