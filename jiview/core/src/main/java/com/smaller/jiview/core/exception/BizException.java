package com.smaller.jiview.core.exception;

import com.smaller.jiview.core.pojo.bo.ResultBO;
import com.smaller.jiview.core.message.BaseMessage;
import lombok.Data;

/**
 * Created by xiagf on 2018/11/13.
 */
@Data
public class BizException extends RuntimeException {
    private BaseMessage errorCode = BaseMessage.BIZ_EXCEPTION_DEFAULT;
    private ResultBO result;

    /**
     * 大多数情况下使用这个
     *
     * @param baseMsgCode
     */
    public BizException(BaseMessage baseMsgCode) {
        super();
        this.errorCode = baseMsgCode;
    }

    /**
     * 需要返回错误对象・列表时使用
     *
     * @param result
     * @param baseMsgCode
     */
    public BizException(ResultBO result, BaseMessage baseMsgCode) {
        super();
        this.result = result;
        this.errorCode = baseMsgCode;
    }

    /**
     * Excel导入用,返回动态message
     *
     * @param msg
     */
    public BizException(String msg) {
        super(msg);
    }
}
