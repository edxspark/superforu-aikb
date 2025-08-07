package org.dromara.com.userPackagePurchase.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONParser;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.extern.slf4j.Slf4j;
import org.dromara.com.userPackagePromotion.domain.ComUserPackagePromotion;
import org.dromara.com.userPackagePromotion.mapper.ComUserPackagePromotionMapper;
import org.dromara.com.userPackagePurchase.domain.bo.ComUserPackagePurchaseItemBo;
import org.dromara.com.userPackagePurchase.domain.vo.ComUserPackagePurchaseItems;
import org.dromara.common.core.utils.ServletUtils;
import org.dromara.pay.callback.domain.bo.PayCallbackBo;
import org.dromara.pay.common.service.alipay.AliPayServiceImp;
import org.dromara.pay.common.service.wxpay.WxPayServiceImp;
import org.dromara.pay.enums.PayWayEnum;
import org.dromara.pay.order.domain.PayOrder;
import org.dromara.pay.order.mapper.PayOrderMapper;
import org.dromara.system.domain.bo.SysDictDataBo;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.com.userPackagePurchase.domain.vo.ComUserPackagePurchaseVo;
import org.dromara.com.userPackagePurchase.domain.bo.ComUserPackagePurchaseBo;
import org.dromara.com.userPackagePurchase.service.IComUserPackagePurchaseService;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.system.domain.vo.SysDictDataVo;
import org.dromara.system.service.ISysDictDataService;

/**
 * 用户套餐购买详细
 *
 * @author Lion Li
 * @date 2024-03-21
 */
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/com/userPackagePurchase")
public class ComUserPackagePurchaseController extends BaseController {

    private final IComUserPackagePurchaseService comUserPackagePurchaseService;
    private final ISysDictDataService iSysDictDataService;
    private final AliPayServiceImp aliPayServiceImp;
    private final PayOrderMapper payOrderMapper;
    private final WxPayServiceImp wxPayServiceImp;
    private final ComUserPackagePromotionMapper comUserPackagePromotionMapper;


    /**
     * 查询优惠码
     * */
    /**
     * 订单状态查询
     * @return true: 订单支付成功，false: 订单支付失败
     * */
    @SaCheckLogin
    @GetMapping("/getPromotion/{promotionCode}")
    public R<Object> getPromotion(@PathVariable String promotionCode){
        R<Object> rt = new R<Object>();
        rt.setCode(200);
        ComUserPackagePromotion comUserPackagePromotion = comUserPackagePromotionMapper.selectOne(new LambdaQueryWrapper<ComUserPackagePromotion>().eq(ComUserPackagePromotion::getPromotionCode,promotionCode));
        rt.setData(comUserPackagePromotion);
        return rt;
    }

    /**
     * 订单状态查询
     * @return true: 订单支付成功，false: 订单支付失败
     * */
    @SaCheckLogin
    @GetMapping("/queryOrderStatus/{orderNo}")
    public R<Object> queryOrderStatus(@PathVariable String orderNo){
        R<Object> rt = new R<Object>();
        rt.setCode(200);
        rt.setData(comUserPackagePurchaseService.queryOrderStatus(orderNo));
        return rt;
    }

    /**
     * 支付宝支付回调
     * 1. 验签
     * 2. 验参
     * 3. 业务处理
     * */
    @SaIgnore
    @PostMapping("/alipay/callback")
    public String alipayCallBack(@RequestParam Map<String,String> params){

        long dateSteam = new Date().getTime();
        log.info("支付宝支付回调-START-"+dateSteam);

        String rt = "FAIL";
        // 1. 验签
        boolean signCheck = aliPayServiceImp.signCheck(params);
        if(signCheck){
            // 2. 验参
            // 支付状态
            String tradeStatus = params.get("trade_status");
            if("TRADE_SUCCESS".equals(tradeStatus)){
                log.info("支付宝支付回调-交易状态成功，params="+params);
            }else{
                log.info("支付宝支付回调-交易状态失败，params="+params);
                return rt;
            }

            // 获取支付订单
            String orderNo = params.get("out_trade_no");
            LambdaQueryWrapper<PayOrder> lqw = Wrappers.lambdaQuery();
            lqw.eq(PayOrder::getOrderNo, orderNo);
            PayOrder payOrder = payOrderMapper.selectOne(lqw);
            if(null != payOrder){

                // 3. 业务处理
                // 判断清算是否已成功
                if(1L==payOrder.getStatus()){
                    log.error("支付宝支付回调-回调清算已成功，重复回调拦截，params="+params.toString());
                    rt = "success";
                    return rt;
                }

                // 支付成功业务处理
                boolean processResult = comUserPackagePurchaseService.paySuccess(PayWayEnum.ALIPAY.getKey(),params);
                if(processResult){
                    rt = "success";
                    return rt;
                }
            }
        }else{
            log.error("支付宝支付回调-验签失败，params="+params.toString());
            rt = "FAIL";
        }

        log.info("支付宝支付回调-END-"+dateSteam);

        return rt;
    }

    /**
     * 微信支付回调
     * 1. 验签
     * 2. 验参
     * 3. 业务处理
     * */
    @SaIgnore
    @PostMapping("/wxpay/callback")
    public String wxpayCallBack(HttpServletRequest request) throws GeneralSecurityException, IOException {
        long dateSteam = new Date().getTime();
        log.info("微信支付回调-START-"+dateSteam);

        String rt = "";
        JSONObject rtJSON = JSONUtil.createObj();
        rtJSON.set("code","FAIL");
        rtJSON.set("message","清算处理失败。");

        String requestBody = ServletUtils.getBody(request);

        // 1. 验签
        boolean signCheck = wxPayServiceImp.signCheck(request,requestBody);
        if(signCheck){
            log.info("微信支付回调-验签成功");

            // 参数
            JSONObject paramObject = JSONUtil.parseObj(requestBody);
            String decryParams = wxPayServiceImp.decryptParams(paramObject);
            Map<String,String> params = JSON.parseObject(decryParams,Map.class);

            log.info("微信支付回调-解密后参数="+decryParams);
            JSONObject decryParamObject = JSONUtil.parseObj(decryParams);

            // 2. 验参
            // 支付状态
            String tradeStatus = paramObject.getStr("event_type");
            if("TRANSACTION.SUCCESS".equals(tradeStatus)){
                log.info("微信支付回调-交易状态成功，params="+paramObject.toString());
            }else{
                log.info("微信支付回调-交易状态失败，params="+paramObject.toString());
                return rt;
            }

            // 获取支付订单
            String orderNo = decryParamObject.getStr("out_trade_no");
            LambdaQueryWrapper<PayOrder> lqw = Wrappers.lambdaQuery();
            lqw.eq(PayOrder::getOrderNo, orderNo);
            PayOrder payOrder = payOrderMapper.selectOne(lqw);
            if(null != payOrder){
                // 3. 业务处理
                // 判断清算是否已成功
                if(1L==payOrder.getStatus()){
                    log.error("微信支付回调-回调清算已成功，重复回调拦截，requestBody="+requestBody);
                    rtJSON.set("code","SUCCESS");
                    rtJSON.set("message","清算处理成功。");
                    return rtJSON.toString();
                }

                // 支付成功业务处理
                boolean processResult = comUserPackagePurchaseService.paySuccess(PayWayEnum.WXPAY.getKey(), params);
                if(processResult){
                    log.error("微信支付回调-回调清算已成功，订单号="+orderNo);
                    rtJSON.set("code","SUCCESS");
                    rtJSON.set("message","清算处理成功。");
                }
            }
        }else{
            log.error("微信支付回调-验签失败，requestBody="+requestBody);
            rtJSON.set("code","FAIL");
            rtJSON.set("message","验签失败！");
        }

        log.info("微信支付回调-返回值="+rtJSON.toString());
        log.info("微信支付回调-END-"+dateSteam);

        return rtJSON.toString();
    }

    /**
     * 获取显示用户套餐列表
     * 1. 获取字典配置(key=com_user_package)
     * 2. 填充单位、价格、最低起售、描述
     */
    @SaCheckLogin
    @GetMapping("/listItems")
    public TableDataInfo<ComUserPackagePurchaseItems> list() {

        // 1. 获取字典配置(key=com_user_package)
        SysDictDataBo dictDataFileTypeBo = new SysDictDataBo();
        dictDataFileTypeBo.setDictType("com_user_package");
        List<SysDictDataVo> userPackageList = iSysDictDataService.selectDictDataList(dictDataFileTypeBo);
        List<ComUserPackagePurchaseItems> packageItemList = new ArrayList<ComUserPackagePurchaseItems>();
        for(SysDictDataVo sysDictDataVo:userPackageList){
            ComUserPackagePurchaseItems comUserPackagePurchaseItem = new ComUserPackagePurchaseItems();
            comUserPackagePurchaseItem.setPackageCode(sysDictDataVo.getDictValue());
            comUserPackagePurchaseItem.setPackageName(sysDictDataVo.getDictLabel());

            // 2. 填充单位、价格、折扣、最低起售、描述
            String remark = sysDictDataVo.getRemark();
            log.info("com_user_package."+sysDictDataVo.getDictValue()+".remark="+remark);
            JSONObject remarkJSONObject = JSONUtil.parseObj(remark);
            comUserPackagePurchaseItem.setUnit(remarkJSONObject.getStr("unit"));
            comUserPackagePurchaseItem.setPrice(Float.parseFloat(remarkJSONObject.getStr("price")));
            comUserPackagePurchaseItem.setDiscount(remarkJSONObject.getStr("discount"));
            comUserPackagePurchaseItem.setMin(Integer.parseInt(remarkJSONObject.getStr("min")));
            comUserPackagePurchaseItem.setDesc(remarkJSONObject.getStr("desc"));
            packageItemList.add(comUserPackagePurchaseItem);
        }

        TableDataInfo<ComUserPackagePurchaseItems> rt = new TableDataInfo<ComUserPackagePurchaseItems>();
        rt.setTotal(packageItemList.size());
        rt.setCode(200);
        rt.setRows(packageItemList);
        return rt;
    }


    /**
     * 用户套餐购买详细
     */
    @SaCheckLogin
    @Log(title = "用户套餐购买详细", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/purchaseItem")
    public R<String> purchaseItem(@Validated(AddGroup.class) @RequestBody ComUserPackagePurchaseItemBo bo) {
        return comUserPackagePurchaseService.purchaseItem(bo);
    }


    /**
     * 查询用户套餐购买详细列表
     */
    @SaCheckPermission("com:userPackagePurchase:list")
    @GetMapping("/list")
    public TableDataInfo<ComUserPackagePurchaseVo> list(ComUserPackagePurchaseBo bo, PageQuery pageQuery) {
        return comUserPackagePurchaseService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出用户套餐购买详细列表
     */
    @SaCheckPermission("com:userPackagePurchase:export")
    @Log(title = "用户套餐购买详细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(ComUserPackagePurchaseBo bo, HttpServletResponse response) {
        List<ComUserPackagePurchaseVo> list = comUserPackagePurchaseService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户套餐购买详细", ComUserPackagePurchaseVo.class, response);
    }

    /**
     * 获取用户套餐购买详细详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("com:userPackagePurchase:query")
    @GetMapping("/{id}")
    public R<ComUserPackagePurchaseVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(comUserPackagePurchaseService.queryById(id));
    }

    /**
     * 新增用户套餐购买详细
     */
    @SaCheckPermission("com:userPackagePurchase:add")
    @Log(title = "用户套餐购买详细", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody ComUserPackagePurchaseBo bo) {
        return toAjax(comUserPackagePurchaseService.insertByBo(bo));
    }

    /**
     * 修改用户套餐购买详细
     */
    @SaCheckPermission("com:userPackagePurchase:edit")
    @Log(title = "用户套餐购买详细", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody ComUserPackagePurchaseBo bo) {
        return toAjax(comUserPackagePurchaseService.updateByBo(bo));
    }

    /**
     * 删除用户套餐购买详细
     *
     * @param ids 主键串
     */
    @SaCheckPermission("com:userPackagePurchase:remove")
    @Log(title = "用户套餐购买详细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(comUserPackagePurchaseService.deleteWithValidByIds(List.of(ids), true));
    }
}
