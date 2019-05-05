package com.smaller.jiview.core.message;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by xiagf on 2018/11/13.
 */
@Data
public class BaseMessage implements Serializable {
    private static final long serialVersionUID = -2168284426954059625L;

    public BaseMessage(String msgCode, String msg) {
        this.msgCode = msgCode;
        this.msg = msg;
    }

    private final String msgCode;
    private final String msg;

    /**
     * 信息代码格式：ABCCCCD
     * 第1位：0,系统信息,1.业务信息
     * 第2位：0,共通,1.app端,2.Admin端,3.Batch端,4.MQ端,5.支付宝,6.微信,7.MSDP端
     * 第3~6位：顺序增长,代表不同的业务
     * 第7位：0.成功信息,1.一种异常信息,2.另一种异常信息,3.第三种异常信息,以此类推
     */
    public static final BaseMessage BIZ_EXCEPTION_DEFAULT = new BaseMessage("1000001", "业务异常");
    public static final BaseMessage INPUT_INVAILD_EXCEPTION = new BaseMessage("1000011", "输入参数错误");
    public static final BaseMessage SYS_EXCEPTION_DEFAULT = new BaseMessage("000000", "系统异常");
}
