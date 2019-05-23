package com.smaller.jiview.core.exception.handler;

import com.smaller.jiview.core.exception.BizException;
import com.smaller.jiview.core.exception.SysException;
import com.smaller.jiview.core.message.BaseMessage;
import com.smaller.jiview.core.pojo.bo.ResultBO;
import com.smaller.jiview.core.properties.ServerProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;


/**
 * @author xiagf on 2018/11/13.
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class BaseExceptionHandler {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ServerProperties serverProperties;

    /**
     * 输入参数异常handler
     *
     * @param ex
     * @return ResultBO
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResultBO processError(MethodArgumentNotValidException ex) throws IOException {
        log.error("BaseExceptionHandle→MethodArgumentNotValidException");
        log.error(ExceptionUtils.getStackTrace(ex));

        BaseMessage msgCodeObj = BaseMessage.INPUT_INVAILD_EXCEPTION;
        String msgCode = msgCodeObj.getMsgCode();

        String msgs = "";
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            msgs += error.getDefaultMessage() + ";";
        }
        String msg = Optional.ofNullable(msgs).orElse(msgCodeObj.getMsg());

        return setMsg(msgCode, msg);
    }

    /**
     * 业务异常handler
     *
     * @param ex
     * @return ResultBO
     */
    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResultBO processError(BizException ex) {
        log.warn("BaseExceptionHandle→BizException");

        BaseMessage msgCodeObj = Optional.ofNullable(ex.getErrorCode()).orElse(BaseMessage.BIZ_EXCEPTION_DEFAULT);
        String msgCode = msgCodeObj.getMsgCode();
        String msg = Optional.ofNullable(ex.getMessage()).orElse(msgCodeObj.getMsg());

        return setMsg(ex.getResult(), msgCode, msg);
    }

    /**
     * 系统异常handler
     *
     * @param ex
     * @return ResultBO
     */
    @ExceptionHandler(SysException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResultBO processError(SysException ex) {
        log.error("BaseExceptionHandle→SysException");
        log.error(ExceptionUtils.getStackTrace(ex));

        BaseMessage msgCodeObj = Optional.ofNullable(ex.getErrorCode()).orElse(BaseMessage.SYS_EXCEPTION_DEFAULT);
        String msgCode = msgCodeObj.getMsgCode();
        String msg = Optional.ofNullable(ex.getMessage()).orElse(msgCodeObj.getMsg());

        return setMsg(msgCode, msg);
    }

    /**
     * 未预测异常handler
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResultBO processError(Exception ex) {
        log.error("BaseExceptionHandle→Exception");
        log.error(ExceptionUtils.getStackTrace(ex));

        BaseMessage msgCodeObj = BaseMessage.SYS_EXCEPTION_DEFAULT;
        String msgCode = msgCodeObj.getMsgCode();
        String msg = Optional.ofNullable(ex.getMessage()).orElse(msgCodeObj.getMsg());

        return setMsg(msgCode, msg);
    }

    private ResultBO setMsg(String msgCode, String msg) {
        log.error("msgCode:" + msgCode);
        log.error("msg:" + msg);
        return setMsg(new ResultBO(), msgCode, msg);
    }

    private ResultBO setMsg(ResultBO result, String msgCode, String msg) {
        result = Optional.ofNullable(result).orElse(new ResultBO());

        result.setMsgCode(msgCode);
        result.setMsg(msg);

        log.error("msgCode:" + msgCode);
        log.error("msg:" + msg);
        return result;
    }
}
