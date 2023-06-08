package com.pan.common.execption;


import com.pan.common.entity.RespenseBean;
import com.pan.common.execption.TokenExecption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

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
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public RespenseBean exeptionHandler(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder sb = new StringBuilder();
        if (bindingResult.hasFieldErrors()) {
            if (bindingResult instanceof BeanPropertyBindingResult){
                BeanPropertyBindingResult propertyBindingResult = (BeanPropertyBindingResult) bindingResult;
                List<FieldError> fieldErrors = propertyBindingResult.getFieldErrors();
                fieldErrors.stream().forEach(fieldError -> {
                    String field = fieldError.getField();
                    String defaultMessage = fieldError.getDefaultMessage();
                    sb.append(defaultMessage);
                });
            }
        }
        RespenseBean respenseBean = new RespenseBean(sb.toString(), null, 500);
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
