package com.mrx.www.common.exception;

import lombok.Data;

/**
 * 自定义异常.
 *
 * @author Mei Ruoxiao
 * @since 2020/05/28
 */
@Data
public class BaseException extends RuntimeException {
    private String code;
    private String message;

    public BaseException(String message) {
        super(message);
        this.message = message;
    }

    public BaseException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

}
