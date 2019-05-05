package com.smaller.jiview.core.exception;

import com.smaller.jiview.core.message.BaseMessage;
import lombok.Data;

/**
 * Created by xiagf on 2018/11/13.
 */
@Data
public class SysException extends RuntimeException {
    private BaseMessage errorCode = BaseMessage.SYS_EXCEPTION_DEFAULT;

    public SysException() {
        super();
    }

    public SysException(String msg) {
        super(msg);
    }

    public SysException(Throwable e) {
        super(e);
    }
}
