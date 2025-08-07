package org.dromara.com.userPackagePurchase.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse;
import lombok.extern.slf4j.Slf4j;
import org.dromara.com.userPackageDetail.domain.ComUserPackageDetail;
import org.dromara.com.userPackageDetail.mapper.ComUserPackageDetailMapper;
import org.dromara.com.userPackagePromotion.domain.ComUserPackagePromotion;
import org.dromara.com.userPackagePromotion.mapper.ComUserPackagePromotionMapper;
import org.dromara.com.utils.enums.PackageConfigEnum;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.utils.DateUtils;
import org.dromara.pay.callback.domain.PayCallback;
import org.dromara.pay.callback.mapper.PayCallbackMapper;
import org.dromara.pay.common.domain.bo.CreateOrderBo;
import org.dromara.com.userPackagePurchase.domain.bo.ComUserPackagePurchaseItemBo;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.pay.common.service.alipay.AliPayServiceImp;
import org.dromara.pay.common.service.wxpay.WxPayServiceImp;
import org.dromara.pay.enums.PayWayEnum;
import org.dromara.pay.order.domain.PayOrder;
import org.dromara.pay.order.mapper.PayOrderMapper;
import org.dromara.system.domain.bo.SysDictDataBo;
import org.dromara.system.domain.vo.SysDictDataVo;
import org.dromara.system.service.ISysDictDataService;
import org.springframework.stereotype.Service;
import org.dromara.com.userPackagePurchase.domain.bo.ComUserPackagePurchaseBo;
import org.dromara.com.userPackagePurchase.domain.vo.ComUserPackagePurchaseVo;
import org.dromara.com.userPackagePurchase.domain.ComUserPackagePurchase;
import org.dromara.com.userPackagePurchase.mapper.ComUserPackagePurchaseMapper;
import org.dromara.com.userPackagePurchase.service.IComUserPackagePurchaseService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 用户套餐购买详细Service业务层处理
 *
 * @author Lion Li
 * @date 2024-03-21
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ComUserPackagePurchaseServiceImpl implements IComUserPackagePurchaseService {

    private final ComUserPackagePurchaseMapper baseMapper;
    private final ISysDictDataService iSysDictDataService;
    private final WxPayServiceImp wxPayServices;
    private final AliPayServiceImp aliPayService;
    private final PayCallbackMapper payCallbackMapper;
    private final PayOrderMapper payOrderMapper;
    private final ComUserPackagePurchaseMapper comUserPackagePurchaseMapper;
    private final ComUserPackageDetailMapper comUserPackageDetailMapper;
    private final ComUserPackagePromotionMapper comUserPackagePromotionMapper;

    /**
     * 查询支付状态
     * 1. 查询支付订单
     * 2. 返回状态
     * */
    @Override
    public Boolean queryOrderStatus(String orderNo){
        boolean rt =false;

        // 1. 查询支付订单
        PayOrder payOrder = payOrderMapper.selectOne(new LambdaQueryWrapper<PayOrder>().eq(PayOrder::getOrderNo,orderNo));
        if(null != payOrder){
            Long status = payOrder.getStatus();
            if(status==1){
                return true;
            }
        }

        // 2. 返回状态
        return rt;
    }


    /**
     * 订单付款成功
     * 1. 更新【支付回调】记录
     * 2. 更新【支付订单】记录
     * 3. 更新【用户套餐购买购买详细】记录
     * 4. 创建或更新【用户权益资源套餐明细】记录
     *    4.1 记录处理
     *    4.2 权益计算处理
     * */
    @Override
    public Boolean paySuccess(String payWay,Map<String,String> params){

        // 获取参数
        String orderNo = "";
        if(PayWayEnum.ALIPAY.getKey().equals(payWay)){
            orderNo = params.get("out_trade_no");
        }else if(PayWayEnum.WXPAY.getKey().equals(payWay)){
            orderNo = params.get("out_trade_no");
        }

        // 1. 更新【支付回调】记录
        PayCallback payCallbackOne = payCallbackMapper.selectOne(new LambdaQueryWrapper<PayCallback>().eq(PayCallback::getOrderNo,orderNo));
        if(null != payCallbackOne){
            PayCallback payCallback = new PayCallback();
            payCallback.setId(payCallbackOne.getId());
            payCallback.setOrderNo(Long.parseLong(orderNo));
            payCallback.setStatus(1L);
            payCallback.setCount((payCallbackOne.getCount()+1));
            payCallbackMapper.updateById(payCallback);
        }else{
            log.error("支付成功，但【支付回调】记录为空，订单号："+orderNo);
            return false;
        }

        // 2. 更新【支付订单】记录
        PayOrder payOrderOne = payOrderMapper.selectOne(new LambdaQueryWrapper<PayOrder>().eq(PayOrder::getOrderNo,orderNo));
        if(null != payOrderOne){
            payOrderOne.setId(payOrderOne.getId());
            payOrderOne.setStatus(1L);
            payOrderMapper.updateById(payOrderOne);
        }else{
            log.error("支付成功，但【支付订单】记录为空，订单号："+orderNo);
            return false;
        }

        // 3. 更新【用户套餐购买购买详细】记录
        ComUserPackagePurchase comUserPackagePurchaseOne = comUserPackagePurchaseMapper.selectById(Long.parseLong(orderNo));
        if(null != comUserPackagePurchaseOne){
            comUserPackagePurchaseOne.setPayNo(""+payOrderOne.getId());
            comUserPackagePurchaseMapper.updateById(comUserPackagePurchaseOne);
        }else{
            log.error("支付成功，但【用户套餐购买购买详细】记录为空，订单号："+orderNo);
            return false;
        }

        // 4. 创建或更新【用户权益资源套餐明细】记录
        // 4.1 记录处理
        String packageCode = comUserPackagePurchaseOne.getPackageCode();
        String packageName = comUserPackagePurchaseOne.getPackageName();
        String unit        = comUserPackagePurchaseOne.getUnit();
        int convertUtil    = comUserPackagePurchaseOne.getConvertUtil();
        int number         = comUserPackagePurchaseOne.getNumber();
        long totalValue    = convertUtil*number; // 本次购买数量（换算单位*数量）

        long userId = comUserPackagePurchaseOne.getLinkUserId();
        ComUserPackageDetail comUserPackageDetailOne = comUserPackageDetailMapper.selectOne(new LambdaQueryWrapper<ComUserPackageDetail>()
            .eq(ComUserPackageDetail::getLinkUserId,userId)
            .eq(ComUserPackageDetail::getPackageCode,packageCode)
        );

        // 创建
        if(null == comUserPackageDetailOne){
            ComUserPackageDetail comUserPackageDetail = new ComUserPackageDetail();
            comUserPackageDetail.setLinkUserId(userId);
            comUserPackageDetail.setPackageCode(packageCode);
            comUserPackageDetail.setPackageName(packageName);
            comUserPackageDetail.setUnit(unit);
            comUserPackageDetail.setConvertUtil(convertUtil);
            comUserPackageDetail.setValue(totalValue);
            comUserPackageDetailMapper.insert(comUserPackageDetail);
        }else{
            // 更新
            long originalTotalValue = comUserPackageDetailOne.getValue();
            long newTotalValue = originalTotalValue+totalValue;
            comUserPackageDetailOne.setValue(newTotalValue);
            comUserPackageDetailMapper.updateById(comUserPackageDetailOne);
        }

        // 4.2 权益计算处理
        String appValue    = comUserPackageDetailOne.getAppValue();
        String appValueNew = "";
        // 开通专业会员
        if(PackageConfigEnum.MEMBER_PACKAGE.getKey().equals(packageCode)){
            Date nowDate = DateUtils.getNowDate();

            // 新开会员和过期会员处理
            if(StringUtils.isEmpty(appValue) || (nowDate.compareTo(DateUtils.parseDate(appValue))>0)){
                Date newAppValueAdd = DateUtils.addYears(new Date(),number); // number: 代表加几年
                appValueNew = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,newAppValueAdd);
            }else{
                Date originalYearDate = DateUtils.parseDate(appValue);
                Date newAppValueAdd = DateUtils.addYears(originalYearDate,number);
                appValueNew = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,newAppValueAdd);
            }

        }else if(PackageConfigEnum.AI_TOKEN_PACKAGE.getKey().equals(packageCode)){
            // AI账户充值
            if(StringUtils.isEmpty(appValue)){
                appValueNew = ""+totalValue;
            }else{
                long originalAppValue = Long.parseLong(appValue);
                long appValueAdd = originalAppValue + totalValue;
                appValueNew = ""+appValueAdd;
            }
        }else{
            log.error("支付成功，但【用户权益套餐】记录为空，订单号："+orderNo+",权益套餐编号:"+packageCode);
            return false;
        }

        comUserPackageDetailOne.setAppValue(appValueNew);
        comUserPackageDetailMapper.updateById(comUserPackageDetailOne);

        return true;
    }


    /**
     * 赠送会员
     * 1. 查询出会员记录
     * 2. 赠送处理
     * */
    @Override
    public Boolean giftToMember(Long userId,int monthCount){

        // 赠送会员
        ComUserPackageDetail comUserPackageDetailOne = comUserPackageDetailMapper.selectOne(new LambdaQueryWrapper<ComUserPackageDetail>()
            .eq(ComUserPackageDetail::getLinkUserId,userId)
            .eq(ComUserPackageDetail::getPackageCode,PackageConfigEnum.MEMBER_PACKAGE)
        );

        String appValue    = comUserPackageDetailOne.getAppValue();
        String appValueNew = "";

        // 2. 赠送处理
        Date nowDate = DateUtils.getNowDate();
        // 新开会员和过期会员处理
        if(StringUtils.isEmpty(appValue) || (nowDate.compareTo(DateUtils.parseDate(appValue))>0)){
            Date newAppValueAdd = DateUtils.addMonths(new Date(),monthCount); // number: 代表加几年
            appValueNew = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,newAppValueAdd);
        }else{
            Date originalYearDate = DateUtils.parseDate(appValue);
            Date newAppValueAdd = DateUtils.addMonths(originalYearDate,monthCount);
            appValueNew = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,newAppValueAdd);
        }
        comUserPackageDetailOne.setAppValue(appValueNew);
        comUserPackageDetailMapper.updateById(comUserPackageDetailOne);
        return true;
    }

    /**
     * 查询用户套餐购买详细
     */
    @Override
    public ComUserPackagePurchaseVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 查询用户套餐购买详细列表
     */
    @Override
    public TableDataInfo<ComUserPackagePurchaseVo> queryPageList(ComUserPackagePurchaseBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<ComUserPackagePurchase> lqw = buildQueryWrapper(bo);
        Page<ComUserPackagePurchaseVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询用户套餐购买详细列表
     */
    @Override
    public List<ComUserPackagePurchaseVo> queryList(ComUserPackagePurchaseBo bo) {
        LambdaQueryWrapper<ComUserPackagePurchase> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<ComUserPackagePurchase> buildQueryWrapper(ComUserPackagePurchaseBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<ComUserPackagePurchase> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getLinkUserId() != null, ComUserPackagePurchase::getLinkUserId, bo.getLinkUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getPackageCode()), ComUserPackagePurchase::getPackageCode, bo.getPackageCode());
        lqw.like(StringUtils.isNotBlank(bo.getPackageName()), ComUserPackagePurchase::getPackageName, bo.getPackageName());
        return lqw;
    }

    /**
     * 新增用户套餐购买详细
     */
    @Override
    public Boolean insertByBo(ComUserPackagePurchaseBo bo) {
        ComUserPackagePurchase add = MapstructUtils.convert(bo, ComUserPackagePurchase.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 用户套餐购买详细
     * 1. 获取套餐字典信息
     * 2. 创建权益套餐购买订单
     * 3. 创建支付订单
     *
     * @return GRCode 二维码内容
     */
    @Override
    public R<String> purchaseItem(ComUserPackagePurchaseItemBo bo) {

        long dateSteam = new Date().getTime();
        log.info("用户套餐采购-START-"+dateSteam);
        String rtQRCodeStr  = "";

        // 1. 获取套餐字典信息
        // 1. 获取字典配置(key=com_user_package)
        SysDictDataBo dictDataFileTypeBo = new SysDictDataBo();
        dictDataFileTypeBo.setDictType("com_user_package");
        List<SysDictDataVo> userPackageList = iSysDictDataService.selectDictDataList(dictDataFileTypeBo);
        SysDictDataVo userPackageDictVo = null;
        for(SysDictDataVo sysDictDataVo:userPackageList){
            if(bo.getPackageCode().equals(sysDictDataVo.getDictValue())){
                userPackageDictVo = sysDictDataVo;
                break;
            }
        }

        String remark = userPackageDictVo.getRemark();
        JSONObject remarkJSONObject = JSONUtil.parseObj(remark);
        int convert = Integer.parseInt(remarkJSONObject.getStr("convert"));
        String unit = remarkJSONObject.getStr("unit");
        float price = Float.parseFloat(remarkJSONObject.getStr("price"));
        int number  = bo.getNumber();
        String promotionCode = bo.getPromotion();
        int promotionValue   = 0;

        // 支付金额（计算优惠码）
        float total = number*price;
        if(number>1){
            JSONArray discountJSONAry = remarkJSONObject.getJSONArray("discount");
            for(int i=0;i<discountJSONAry.size();i++){
                JSONObject jsonObject = discountJSONAry.getJSONObject(i);
               if(jsonObject.getStr("key").equals(""+number)){
                   total = Integer.parseInt(jsonObject.getStr("value"));
                   break;
               }
            }
        }
        if(StringUtils.isNotEmpty(bo.getPromotion())){
            ComUserPackagePromotion comUserPackagePromotion = comUserPackagePromotionMapper.selectOne(new LambdaQueryWrapper<ComUserPackagePromotion>()
                .eq(ComUserPackagePromotion::getPromotionCode,promotionCode));
            if(null != comUserPackagePromotion){
                promotionValue = comUserPackagePromotion.getPromotionValue();
                float totalFinal = total - promotionValue;
                if(totalFinal<=0){
                    totalFinal = 1;
                }else{
                    total = totalFinal;
                }
            }
        }

        String packageName = userPackageDictVo.getDictLabel();

        // 2. 创建权益套餐购买订单
        ComUserPackagePurchase addBo = new ComUserPackagePurchase();
        addBo.setLinkUserId( LoginHelper.getLoginUser().getUserId());
        addBo.setPackageCode(bo.getPackageCode());
        addBo.setPackageName(packageName);
        addBo.setConvertUtil(convert);
        addBo.setUnit(unit);
        addBo.setPrice(price);
        addBo.setNumber(number);
        addBo.setTotal(total);
        addBo.setPromotionCode(promotionCode);
        addBo.setPromotionValue(promotionValue);
        baseMapper.insert(addBo);

        // 3. 创建支付订单
        CreateOrderBo createOrderBo = new CreateOrderBo();
        createOrderBo.setTradeNo(""+addBo.getId());
        createOrderBo.setSubject(packageName);
        createOrderBo.setTotal(""+total);
        createOrderBo.setPayWayCode(bo.getPayWay());
        if(PayWayEnum.WXPAY.getKey().equals(bo.getPayWay())){
            // 微信支付
            PrepayResponse response =  (PrepayResponse)wxPayServices.createOrder(createOrderBo);
            rtQRCodeStr = response.getCodeUrl();
        }else if(PayWayEnum.ALIPAY.getKey().equals(bo.getPayWay())){
            // 支付宝支付
            AlipayTradePrecreateResponse response = (AlipayTradePrecreateResponse)aliPayService.createOrder(createOrderBo);
            rtQRCodeStr = response.getQrCode();
        }

        log.info("用户套餐采购-订单号="+createOrderBo.getTradeNo());
        log.info("用户套餐采购-END-"+dateSteam);

        R<String> rt = new R<>();
        rt.setCode(200);
        rt.setMsg(rtQRCodeStr);
        rt.setData(createOrderBo.getTradeNo());
        return rt;
    }


    /**
     * 修改用户套餐购买详细
     */
    @Override
    public Boolean updateByBo(ComUserPackagePurchaseBo bo) {
        ComUserPackagePurchase update = MapstructUtils.convert(bo, ComUserPackagePurchase.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(ComUserPackagePurchase entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 批量删除用户套餐购买详细
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteBatchIds(ids) > 0;
    }
}
