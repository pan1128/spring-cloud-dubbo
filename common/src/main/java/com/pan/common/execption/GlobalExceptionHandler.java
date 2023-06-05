package com.pan.common.execption;


import com.pan.common.entity.RespenseBean;
import com.pan.common.execption.TokenExecption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = {Throwable.class})
    public RespenseBean exeptionHandler(Throwable e){
        log.error(e.getMessage(), e);
        RespenseBean respenseBean = new RespenseBean(e.toString(), null, 500);
        return respenseBean;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = {TokenExecption.class})
    public RespenseBean exeptionHandler(TokenExecption e){
        log.error(e.getMessage(), e);
        RespenseBean respenseBean = new RespenseBean(e.getMessage(), null, 500);
        return respenseBean;
    }
}
