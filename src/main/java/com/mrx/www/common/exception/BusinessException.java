package com.mrx.www.common.exception;

/**
 * 业务异常.
 *
 * @author Mei Ruoxiao
 * @since 2020/05/28
 */
public class BusinessException extends BaseException {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String errorCode, String message) {
        super(errorCode, message);
    }
}
