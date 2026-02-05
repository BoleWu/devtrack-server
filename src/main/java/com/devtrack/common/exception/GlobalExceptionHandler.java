package com.devtrack.common.exception;

import com.devtrack.common.result.R;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    public R<?> handleServiceException(ServiceException e) {
        return R.error(e.getMessage());
    }

    @ExceptionHandler(LoginException.class)
    public R<?> handleLoginException(LoginException e) {
        return R.fail("失败",e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String defaultMessage = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        return R.error(defaultMessage);
    }

    @ExceptionHandler(BindException.class)
    public R<?> handleBindException(BindException e) {
        String defaultMessage = Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage();
        return R.error(defaultMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public R<?> handleConstraintViolationException(ConstraintViolationException e) {
        String defaultMessage = e.getConstraintViolations().iterator().next().getMessage();
        return R.error(defaultMessage);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        String message = e.getParameterName() + "参数缺失";
        return R.error(message);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public R<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String message = e.getName() + "参数类型不匹配";
        return R.error(message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return R.error("缺少请求体或请求体格式错误");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return R.error("请求方法不支持");
    }

    @ExceptionHandler(Exception.class)
    public R<?> handleException(Exception e) {
        return R.fail("失败",e.getMessage());
    }
}