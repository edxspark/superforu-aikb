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
import org.dromara.common.core.exception.base.BaseException;
import org.dromara.common.core.exception.user.UserException;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.common.redis.utils.RedisUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;

/**
 * 登录短信验证码策略
 *
 * @author Michelle.Chung
 */
@Slf4j
@Service("login" + IComUserSmsCodeStrategy.BASE_NAME)
@RequiredArgsConstructor
public class ComUserSendLoginCodeStrategy implements IComUserSmsCodeStrategy {

    private final MsgUtil msgUtil;
    private final ComUserMapper baseMapper;


    /**
     * 用户发送登录验证码
     * 1.查询该手机号是否已被注册
     * 2.查询注册验证码是否已发送且未过期
     * 3.调用发送验证码服务
     * 4.发送验证码后储存到redis，并设置5分钟倒计时
     * @param smsLoginBo
     */
    @Override
    public void sendCode(SmsLoginBo smsLoginBo) {
        String phoneNumber = smsLoginBo.getPhoneNumber();
        String redisKey = GlobalConstants.LOGIN_CODE_KEY + phoneNumber;

        //1.查询该手机号是否已被注册
        ComUser user = baseMapper.selectOne(new LambdaQueryWrapper<ComUser>()
                .eq(ComUser::getUserAccount, phoneNumber));
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", phoneNumber);
            throw new BaseException(ErrorMsg.ERR_COM_USER_NOT_EXIST.getMessage());
        }
        //2.查询注册验证码是否已发送且未过期
        String code = RedisUtils.getCacheObject(redisKey);
        if (StringUtils.isNotBlank(code)) {
            throw new BaseException(ErrorMsg.ERR_COM_SMS_IS_SENT.getMessage());
        }
        //3.调用发送验证码服务
        R<String> objectR = msgUtil.sendCode(phoneNumber);
        log.info("短信发送后返回的code:{}", JsonUtils.toJsonString(objectR));

        //4.发送验证码后储存到redis，并设置5分钟倒计时
        RedisUtils.setCacheObject(redisKey, objectR.getMsg(), Duration.ofSeconds(300));

    }


}
