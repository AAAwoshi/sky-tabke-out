package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex) {
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * SQL 异常处理
     * @param exception
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException exception) {
        // Duplicate entry 'lisi' for key 'employee.idx_username'
        String message = exception.getMessage();
        if (message.contains("Duplicate entry")) {   // 如果获取的异常包含 Duplicate entry 字符串
            String[] split = message.split(" ");    // 将异常信息按照空格分隔
            String username = split[2];
            String mes = username + MessageConstant.ALREADY_EXISTS;
            return Result.success(mes);
        } else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }

}
