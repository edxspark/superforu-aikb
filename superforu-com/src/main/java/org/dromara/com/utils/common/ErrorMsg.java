package org.dromara.com.utils.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMsg {

    ERR_COM_USER_EXIST("ERR_COM_0001", "用户已存在"),

    ERR_COM_USER_NOT_EXIST("ERR_COM_0002", "用户不存在"),

    ERR_COM_SMS_IS_SENT("ERR_COM_0003", "短信验证码已发送，请查阅"),

    ERR_COM_USER_ABNORMAL("ERR_COM_0004", "当前用户存在异常"),

    ERR_COM_TEAM_MATE_NOT_EXIST("ERR_COM_0005", "该团队成员不存在"),

    ERR_COM_ROLE_MISMATCH("ERR_COM_0006", "角色不符"),

    ERR_COM_TEAM_MATE_EXIST("ERR_COM_0007", "该团队成员已存在"),

    ERR_COM_TEAM_NAME_EXIST("ERR_COM_0007", "该团队名称已存在"),

    ERR_COM_INSERT_ERR("ERR_COM_0008", "新增出现异常"),

    ERR_COM_TYPE_MISMATCH("ERR_COM_0009", "类型不符"),

    ERR_COM_FILE_TYPE_EXIST("ERR_COM_0010", "文件类型已存在"),

    ERR_COM_UPDATE_ERR("ERR_COM_0011", "更新出现异常"),

    ERR_COM_CAN_NOT_REPEAT("ERR_COM_0012","不可重复"),

    ERR_COM_DATA_NOT_EXIST("ERR_COM_0013","数据不存在");

    private final String code;
    private final String message;

}
