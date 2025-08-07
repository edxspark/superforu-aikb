package org.dromara.com.user.service;


import org.dromara.com.user.domain.ComLoginBody;
import org.dromara.com.user.domain.ComRegisterBody;
import org.dromara.com.utils.loginUtil.vo.LoginVo;
import org.dromara.system.domain.SysClient;

/**
 * 用户注册
 *
 * @author JackLiao
 */
public interface IComUserRegisterService {

    /**
     * 注册
     */
    LoginVo register(ComRegisterBody comRegisterBody, SysClient client);


}
