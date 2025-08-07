package org.dromara.kb.utils.common;

public enum ErrorMsg {

    ERR_KB_KB_SHARE_DOUBLE            ("ERR_KB_0003","知识库已存在分享记录"),
    ERR_KB_FILE_NAME_DOUBLE           ("ERR_KB_0002","当前目录存在同名"),
    ERR_KB_FILE_NAME_ILLEGAL          ("ERR_KB_0001","文件名称含有非法字符");


    String code    = "";
    String message = "";

    private ErrorMsg(String code1, String message) {
        this.code = code1;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }
    public String getMessage() {
        return this.message;
    }
}
