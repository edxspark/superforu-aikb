package org.dromara.kb.utils.common;

public enum Msg {

    MSG_KB_FAILURE                    ("MSG_KB_0002","处理失败"),
    MSG_KB_SUCCESS                    ("MSG_KB_0001","处理成功");


    String code    = "";
    String message = "";

    private Msg(String code1, String message) {
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
