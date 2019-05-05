package com.smaller.jiview.admin.exception.handler;

import com.smaller.jiview.core.exception.handler.BaseExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by xiagf on 2018/11/13.
 */
@ControllerAdvice
@ResponseBody
public class AdminExceptionHandler extends BaseExceptionHandler {}
