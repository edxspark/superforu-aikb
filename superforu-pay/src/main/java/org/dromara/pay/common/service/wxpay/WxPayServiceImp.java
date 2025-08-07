package org.dromara.pay.common.service.wxpay;

import cn.hutool.json.JSONObject;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import com.wechat.pay.java.service.partnerpayments.app.model.Transaction;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import com.wechat.pay.java.service.payments.nativepay.model.Amount;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest;
import com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.pay.callback.domain.PayCallback;
import org.dromara.pay.callback.mapper.PayCallbackMapper;
import org.dromara.pay.common.domain.bo.CreateOrderBo;
import org.dromara.pay.common.service.IPayService;
import org.dromara.pay.common.service.wxpay.utils.AesUtil;
import org.dromara.pay.order.domain.PayOrder;
import org.dromara.pay.order.mapper.PayOrderMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

/**
 * 微信支付服务
 *
 * 开发文档：https://pay.weixin.qq.com/docs/merchant/apis/native-payment/payment-notice.html
 * */
@Slf4j
@RequiredArgsConstructor
@Service
public class WxPayServiceImp implements IPayService {

    /** 应用ID */
    @Value("${wxpay.appId}")
    private  String appId;

    /** 商户号 */
    @Value("${wxpay.merchantId}")
    private  String merchantId;

    /** 商户API私钥路径 */
    @Value("${wxpay.privateKey}")
    private  String privateKey;

    /** 商户证书序列号 */
    @Value("${wxpay.merchantSerialNumber}")
    private  String merchantSerialNumber;

    /** 商户APIV3密钥 */
    @Value("${wxpay.apiV3Key}")
    private  String apiV3Key;

    /** 回调Domain */
    @Value("${wxpay.notifyDomain}")
    private String notifyDomain;

    /** 回调URL */
    @Value("${wxpay.notifyUrl}")
    private  String notifyUrl;

    private final PayOrderMapper payOrderMapper;
    private final PayCallbackMapper payCallbackMapper;


    /**
     * 创建支付订单（Native支付）
     * 1. 设置参数
     * 2. 发起API调用
     * 3. 处理响应或异常
     * 4. 创建支付订单
     * 5. 创建支付回调
     * */
    @Override
    public Object createOrder(CreateOrderBo createOrderBo) {

        PrepayResponse response = null;

        // 1. 设置参数
        // 使用自动更新平台证书的RSA配置
        // 一个商户号只能初始化一个配置，否则会因为重复的下载任务报错
        Config config = getConfig();

        // 构建service
        NativePayService service = new NativePayService.Builder().config(config).build();
        PrepayRequest request = new PrepayRequest();
        Amount amount = new Amount();
        // 将元转成分
        float total = Float.parseFloat(createOrderBo.getTotal())*100;
        amount.setTotal((int)total);
        request.setAmount(amount);
        request.setAppid(appId);
        request.setMchid(merchantId);
        request.setDescription(createOrderBo.getSubject());
        request.setOutTradeNo(createOrderBo.getTradeNo());
        request.setNotifyUrl(notifyDomain.concat(notifyUrl));

        // 2. 发起API调用
        // 调用下单方法，得到应答
        response = service.prepay(request);

        // 3. 处理响应或异常
        if(null != response){
            // 4. 创建支付订单
            PayOrder payOrder = new PayOrder();
            payOrder.setPayWayCode(createOrderBo.getPayWayCode());
            payOrder.setAmount(Float.parseFloat(createOrderBo.getTotal()));
            payOrder.setSubject(createOrderBo.getSubject());
            payOrder.setPayWayCode(createOrderBo.getPayWayCode());
            payOrder.setOrderNo(Long.parseLong(createOrderBo.getTradeNo()));
            payOrderMapper.insert(payOrder);

            // 5. 创建支付回调
            PayCallback payCallback = new PayCallback();
            payCallback.setOrderNo(Long.parseLong(createOrderBo.getTradeNo()));
            payCallback.setCount(0L);
            payCallback.setStatus(0L);
            payCallbackMapper.insert(payCallback);

        }else{
            log.error("微信支付异常，返回信息："+response.toString());
        }

        return response;
    }

    /**
     * 获取支付配置
     * */
    private Config getConfig(){
        return new RSAAutoCertificateConfig.Builder()
            .merchantId(merchantId)
            .privateKey(privateKey)
            .merchantSerialNumber(merchantSerialNumber)
            .apiV3Key(apiV3Key)
            .build();
    }

    /**
     * 获取支付回调配置
     * */
    private NotificationConfig getNotificationConfig(){
        return new RSAAutoCertificateConfig.Builder()
            .merchantId(merchantId)
            .privateKey(privateKey)
            .merchantSerialNumber(merchantSerialNumber)
            .apiV3Key(apiV3Key)
            .build();
    }


    /**
     * 解密
     * */
    public String decryptParams(JSONObject paramObject) throws GeneralSecurityException, IOException {
        JSONObject resource = paramObject.getJSONObject("resource");
        String associatedData = resource.getStr("associated_data");
        String ciphertext     = resource.getStr("ciphertext");
        String nonce          = resource.getStr("nonce");
        AesUtil aesUtil = new AesUtil(apiV3Key.getBytes(StandardCharsets.UTF_8));
        return aesUtil.decryptToString(associatedData.getBytes(StandardCharsets.UTF_8),nonce.getBytes(StandardCharsets.UTF_8),ciphertext);
    }


    /**
     * 验签
     * 1. 获取参数
     * 2. 验签
     * */
    public boolean signCheck(HttpServletRequest request, String requestBody){
        boolean rt =false;

        // 1. 获取参数
        // 微信支付签名
        String wechatPaySerial = request.getHeader("Wechatpay-Serial");
        // 签名中的随机数
        String wechatpayNonce = request.getHeader("Wechatpay-Nonce");
        // 微信支付签名
        String wechatSignature = request.getHeader("Wechatpay-Signature");
        // 签名中的时间戳
        String wechatTimestamp = request.getHeader("Wechatpay-Timestamp");

        // 构造 RequestParam
        RequestParam requestParam = new RequestParam.Builder()
            .serialNumber(wechatPaySerial)
            .nonce(wechatpayNonce)
            .signature(wechatSignature)
            .timestamp(wechatTimestamp)
            .body(requestBody)
            .build();

        // RSAAutoCertificateConfig
        NotificationConfig config = getNotificationConfig();

        // 初始化 NotificationParser
        NotificationParser parser = new NotificationParser(config);

        try {
            // 2. 验签
            // 以支付通知回调为例，验签、解密并转换成 Transaction
            Transaction transaction = parser.parse(requestParam, Transaction.class);
            rt = true;
        } catch (ValidationException e) {
            // 签名验证失败
            log.error("微信支付验签失败:", e);
        }

        return rt;
    }

}
