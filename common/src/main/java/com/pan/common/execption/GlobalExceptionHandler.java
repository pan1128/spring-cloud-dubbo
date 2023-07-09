package com.pan.common.execption;


import com.pan.common.entity.ResponseBean;
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
    public ResponseBean exeptionHandler(Throwable e){
        log.error(e.getMessage(), e);
        ResponseBean responseBean = new ResponseBean(e.toString(), null, 500);
        return responseBean;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseBean exeptionHandler(MethodArgumentNotValidException e){
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
        ResponseBean responseBean = new ResponseBean(sb.toString(), null, 500);
        return responseBean;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = {TokenExecption.class})
    public ResponseBean exeptionHandler(TokenExecption e){
        log.error(e.getMessage(), e);
        ResponseBean responseBean = new ResponseBean(e.getMessage(), null, 500);
        return responseBean;
    }
}
