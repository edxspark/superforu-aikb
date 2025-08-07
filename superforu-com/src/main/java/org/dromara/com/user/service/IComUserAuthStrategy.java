package org.dromara.com.user.service;


import org.dromara.com.user.domain.ComLoginBody;
import org.dromara.com.utils.loginUtil.vo.LoginVo;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.system.domain.SysClient;

/**
 * 授权策略
 *
 * @author JackLiao
 */
public interface IComUserAuthStrategy {

    String BASE_NAME = "ComUserAuthStrategy";

    /**
     * 登录
     */
    static LoginVo login(ComLoginBody comLoginBody, SysClient client) {
        // 授权类型和客户端id
        String clientId = comLoginBody.getClientId();
        String grantType = comLoginBody.getGrantType();
        String beanName = grantType + BASE_NAME;
        if (!SpringUtils.containsBean(beanName)) {
            throw new ServiceException("授权类型不正确!");
        }
        IComUserAuthStrategy instance = SpringUtils.getBean(beanName);
        instance.validate(comLoginBody);

        //
        return instance.login(clientId, comLoginBody, client);
    }

    /**
     * 参数校验
     */
    void validate(ComLoginBody comLoginBody);

    /**
     * 登录
     */
    LoginVo login(String clientId, ComLoginBody comLoginBody, SysClient client);


}
