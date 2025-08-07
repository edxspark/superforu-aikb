package org.dromara.pay.common.service.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.factory.Factory.Payment;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.pay.callback.domain.PayCallback;
import org.dromara.pay.callback.mapper.PayCallbackMapper;
import org.dromara.pay.common.domain.bo.CreateOrderBo;
import org.dromara.pay.common.service.IPayService;
import org.dromara.pay.order.domain.PayOrder;
import org.dromara.pay.order.mapper.PayOrderMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 支付宝支付
 *
 * 开发文档：https://opendocs.alipay.com/open/194/105072?pathHash=45357796
 * */
@Slf4j
@RequiredArgsConstructor
@Service
public class AliPayServiceImp implements IPayService {

    /** 应用ID */
    @Value("${alipay.appId}")
    private  String appId;

    /** 商户私钥 */
    @Value("${alipay.merchantPrivateKey}")
    private  String merchantPrivateKey;

    /** 支付宝公钥 */
    @Value("${alipay.alipayPublicKey}")
    private  String alipayPublicKey;

    /** 回调Domain */
    @Value("${alipay.notifyDomain}")
    private String notifyDomain;

    /** 回调URL */
    @Value("${alipay.notifyUrl}")
    private  String notifyUrl;

    private final String PROTOCOL     = "https";
    private final String GATEWAY_HOST = "openapi.alipay.com";
    private final String SIGN_TYPE    = "RSA2";
    private final String CHARSET      = "UTF-8";

    private final PayOrderMapper payOrderMapper;
    private final PayCallbackMapper payCallbackMapper;

    /**
     * 创建支付订单（当面付收款二维码）
     * 1. 设置参数
     * 2. 发起API调用
     * 3. 处理响应或异常
     * 4. 创建支付订单
     * 5. 创建支付回调
     * */
    @Override
    public Object createOrder(CreateOrderBo createOrderBo) {

        AlipayTradePrecreateResponse response = null;

        // 1. 设置参数
        Factory.setOptions(getOptions());

        try {
            // 2. 发起API调用
             response = Payment.FaceToFace()
                .preCreate(createOrderBo.getSubject(), createOrderBo.getTradeNo(), createOrderBo.getTotal());

            // 3. 处理响应或异常
            if (ResponseChecker.success(response)) {

                // 4. 创建支付订单
                PayOrder payOrder = new PayOrder();
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

                return response;
            } else {
                log.error("支付宝支付创建订单调用失败，原因：" + response.msg + "，" + response.subMsg);
            }
        } catch (Exception e) {
            log.error("支付宝支付创建订单调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }

        return response;
    }


    /**
     * 获取支付配置
     * */
    private Config getOptions() {
        Config config = new Config();
        config.protocol    = PROTOCOL;
        config.gatewayHost = GATEWAY_HOST;
        config.signType    = SIGN_TYPE;
        config.appId       = appId;
        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = merchantPrivateKey;
        //采用非证书模式
        config.alipayPublicKey = alipayPublicKey;
        //可设置异步通知接收服务地址（可选）
        config.notifyUrl = notifyDomain.concat(notifyUrl);
        return config;
    }


    /**
     * 验签
     * */
    public boolean signCheck(Map<String,String> params){
        boolean rt = false;
        try {
            rt = AlipaySignature.rsaCheckV1(params,alipayPublicKey,CHARSET,SIGN_TYPE);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
        return rt;
    }
}
