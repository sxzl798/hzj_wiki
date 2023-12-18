package com.hzj.wiki.controller;

import com.hzj.wiki.exception.BusinessException;
import com.hzj.wiki.resp.CommonResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    /**
     * 校验异常统一处理
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public CommonResp vaildExceptionHandler(BindException e){
        CommonResp commonResp = new CommonResp();
        LOG.warn("参数校验失败：{}",e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return commonResp;
    }
    /**
     * 业务异常统一处理
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public CommonResp vaildExceptionHandler(BusinessException e){
        CommonResp commonResp = new CommonResp();
        LOG.warn("业务异常：{}",e.getCode().getDesc());
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getCode().getDesc());
        return commonResp;
    }
    /**
     * 系统异常统一处理
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResp vaildExceptionHandler(Exception e){
        CommonResp commonResp = new CommonResp();
        LOG.error("系统异常:",e);
        commonResp.setSuccess(false);
        commonResp.setMessage("系统出现异常，请联系管理员");
        return commonResp;
    }
}
