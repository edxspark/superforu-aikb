package org.dromara.com.utils.loginUtil;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaStorage;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.constant.TenantConstants;
import org.dromara.common.core.constant.UserConstants;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.enums.UserType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@Slf4j
@SaIgnore
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class LoginUtil {



    public static final String LOGIN_USER_KEY = "loginUser";
    public static final String TENANT_KEY = "tenantId";
    public static final String USER_KEY = "userId";
    public static final String CLIENT_KEY = "clientid";


    public static void login(LoginUser comUser, SaLoginModel model) {
        SaStorage storage = SaHolder.getStorage();
        storage.set(LOGIN_USER_KEY, comUser);
        storage.set(TENANT_KEY, comUser.getTenantId());
        storage.set(USER_KEY, comUser.getUserId());
        model = ObjectUtil.defaultIfNull(model, new SaLoginModel());
        StpUtil.login(comUser.getLoginId(),
            model.setExtra(TENANT_KEY, comUser.getTenantId())
                .setExtra(USER_KEY, comUser.getUserId()));
        StpUtil.getSession().set(LOGIN_USER_KEY, comUser);
    }

    /**
     * 获取用户(多级缓存)
     */
    public static LoginUser getLoginUser() {
        LoginUser loginComUser = (LoginUser) SaHolder.getStorage().get(LOGIN_USER_KEY);
        if (loginComUser != null) {
            return loginComUser;
        }
        SaSession session = StpUtil.getSession();
        if (ObjectUtil.isNull(session)) {
            return null;
        }
        loginComUser = (LoginUser) session.get(LOGIN_USER_KEY);
        SaHolder.getStorage().set(LOGIN_USER_KEY, loginComUser);
        return loginComUser;
    }

    /**
     * 获取用户基于token
     */
    public static LoginUser getLoginUser(String token) {
        Object loginId = StpUtil.getLoginIdByToken(token);
        SaSession session = StpUtil.getSessionByLoginId(loginId);
        if (ObjectUtil.isNull(session)) {
            return null;
        }
        return (LoginUser) session.get(LOGIN_USER_KEY);
    }

    /**
     * 获取用户id
     */
    public static Long getUserId() {
        return  Convert.toLong(getExtra(USER_KEY));
    }

    /**
     * 获取租户ID
     */
    public static String getTenantId() {
        return Convert.toStr(getExtra(TENANT_KEY));
    }


    private static Object getExtra(String key) {
        Object obj;
        try {
            obj = SaHolder.getStorage().get(key);
            if (ObjectUtil.isNull(obj)) {
                obj = StpUtil.getExtra(key);
                SaHolder.getStorage().set(key, obj);
            }
        } catch (Exception e) {
            return null;
        }
        return obj;
    }

    /**
     * 获取用户账户
     */
    public static String getUsername() {
        return getLoginUser().getUsername();
    }

    /**
     * 获取用户类型
     */
    public static UserType getUserType() {
        String loginType = StpUtil.getLoginIdAsString();
        return UserType.getUserType(loginType);
    }

    /**
     * 是否为超级管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isSuperAdmin(Long userId) {
        return UserConstants.SUPER_ADMIN_ID.equals(userId);
    }

    public static boolean isSuperAdmin() {
        return isSuperAdmin(getUserId());
    }

    /**
     * 是否为超级管理员
     *
     * @param rolePermission 角色权限标识组
     * @return 结果
     */
    public static boolean isTenantAdmin(Set<String> rolePermission) {
        return rolePermission.contains(TenantConstants.TENANT_ADMIN_ROLE_KEY);
    }

    public static boolean isTenantAdmin() {
        return isTenantAdmin(getLoginUser().getRolePermission());
    }

}
