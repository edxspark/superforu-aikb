package org.dromara.com.user.service;


import org.dromara.com.user.domain.bo.SmsLoginBo;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.system.domain.SysClient;

/**
 * 短信验证码策略
 *
 * @author Michelle.Chung
 */
public interface IComUserSmsCodeStrategy {

    String BASE_NAME = "ComUserSmsCodeStrategy";


    /**
     * 用户发送短信校验(注册及登录验证码)
     * @param smsLoginBo
     * @param sysClient
     */
    static void sendCode(SmsLoginBo smsLoginBo,SysClient sysClient) {
        // 校验类型
        String smsType = smsLoginBo.getSmsType();
        String beanName = smsType + BASE_NAME;
        if (!SpringUtils.containsBean(beanName)) {
            throw new ServiceException("验证码类型不正确!");
        }
        IComUserSmsCodeStrategy instance = SpringUtils.getBean(beanName);
        instance.sendCode(smsLoginBo);
    }

    /**
     * 登录或注册
     */
    void sendCode(SmsLoginBo smsLoginbo);

}
