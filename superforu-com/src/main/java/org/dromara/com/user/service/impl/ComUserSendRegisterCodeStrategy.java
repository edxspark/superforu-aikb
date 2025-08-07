package org.dromara.com.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.com.user.domain.ComUser;
import org.dromara.com.user.domain.bo.SmsLoginBo;
import org.dromara.com.user.mapper.ComUserMapper;
import org.dromara.com.user.service.IComUserSmsCodeStrategy;
import org.dromara.com.utils.common.ErrorMsg;
import org.dromara.com.utils.msgUtil.MsgUtil;
import org.dromara.common.core.constant.GlobalConstants;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.enums.LoginType;
import org.dromara.common.core.exception.base.BaseException;
import org.dromara.common.core.exception.user.UserException;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.web.config.properties.CaptchaProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;


/**
 * 注册短信验证码策略
 *
 * @author JackLiao
 */
@Slf4j
@Service("register" + IComUserSmsCodeStrategy.BASE_NAME)
@RequiredArgsConstructor
public class ComUserSendRegisterCodeStrategy implements IComUserSmsCodeStrategy {

    @Value("${user.password.maxRetryCount}")
    private Integer maxRetryCount;

    @Value("${user.password.lockTime}")
    private Integer lockTime;

    private final MsgUtil msgUtil;

    private final ComUserMapper baseMapper;


    /**
     * 用户发送登录验证码
     * 1.查询该手机号是否已被注册
     * 2.获取用户注册错误次数，默认为0
     *      2.1.查询注册验证码是否已发送且未过期
     * 3.调用发送验证码服务
     * 4.发送验证码后储存到redis，并设置5分钟倒计时
     * @param smsLoginBo
     */
    @Override
    public void sendCode(SmsLoginBo smsLoginBo) {
        String phoneNumber = smsLoginBo.getPhoneNumber();
        String redisCodeKey = GlobalConstants.REGISTER_CODE_KEY + phoneNumber;
        String errorKey = GlobalConstants.PWD_ERR_CNT_KEY + phoneNumber;


        //1.查询该手机号是否已被注册
        ComUser user = baseMapper.selectOne(new LambdaQueryWrapper<ComUser>()
                .eq(ComUser::getUserAccount, phoneNumber));
        if (ObjectUtil.isNotNull(user)) {
            log.info("注册用户：{} 已存在.", phoneNumber);
            throw new BaseException(ErrorMsg.ERR_COM_USER_EXIST.getMessage());
        }
        //2.获取用户注册错误次数，默认为0
        //2.1查询注册验证码是否已发送且未过期
        // 获取用户登录错误次数，默认为0 (可自定义限制策略 例如: key + username + ip)
        int errorNumber = ObjectUtil.defaultIfNull(RedisUtils.getCacheObject(errorKey), 0);
        // 锁定时间内登录 则踢出
        if (errorNumber >= maxRetryCount) {
            throw new UserException(LoginType.SMS.getRetryLimitExceed(), maxRetryCount, lockTime);
        }
        String code = RedisUtils.getCacheObject(redisCodeKey);
        if (StringUtils.isNotBlank(code)) {
            throw new BaseException(ErrorMsg.ERR_COM_SMS_IS_SENT.getMessage());
        }
        //3.调用发送验证码服务
        R<String> objectR = msgUtil.sendCode(phoneNumber);
        log.info("短信发送后返回的code:{}", JsonUtils.toJsonString(objectR));

        //4.发送验证码后储存到redis,并设置5分钟过期时间
        RedisUtils.setCacheObject(redisCodeKey, objectR.getMsg(), Duration.ofSeconds(300));

    }

}
