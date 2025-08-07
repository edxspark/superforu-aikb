package org.dromara.pay.common.domain.vo;

import lombok.Data;

@Data
public class PayVo {

    /**
     * 状态码
     * */
    private String code;

    /**
     * 处理结果消息
     * */
    private String message;

}
