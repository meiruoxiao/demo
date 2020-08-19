package com.mrx.www.common.exception;

import com.mrx.www.common.ResultCode;
import com.mrx.www.common.Rs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.BindException;

/**
 * 全局异常处理.
 *
 * @author Mei Ruoxiao
 * @since 2020/05/27
 */
@Slf4j
@Component
@ControllerAdvice
@ConditionalOnWebApplication
public class GlobalExceptionHandler {
    /**
     * 生产环境
     */
    private static final String ENV_PROD = "prod";

    /**
     * 当前环境
     */
//    @Value("${spring.profiles.active}")
    private String profile;

    /**
     * 业务异常
     *
     * @return 异常结果
     * @parame 异常
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public Rs handleBusinessException(BaseException e) {
        log.error(e.getMessage(), e);
        return new Rs(e.getCode(), e.getMessage());
    }

    /**
     * 自定义异常
     *
     * @return 异常结果
     * @parame 异常
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public Rs handleBaseException(BaseException e) {
        log.error(e.getMessage(), e);
        return new Rs(e.getCode(), e.getMessage());
    }

    /**
     * Controller上一层相关异常
     *
     * @return 异常结果
     * @parame 异常
     */
    @ExceptionHandler({
            NoHandlerFoundException.class,
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            BindException.class,
            MethodArgumentNotValidException.class,
            HttpMediaTypeNotAcceptableException.class,
            ServletRequestBindingException.class,
            ConversionNotSupportedException.class,
            MissingServletRequestPartException.class})
    @ResponseBody
    public Rs handleServletException(Exception e) {
        log.error(e.getMessage(), e);
        String code = ResultCode.ERROR.getCode();
        try {
//            ServletResponseEnum servletExceptionEnum = ServletResponseEnum.valueOf(e.getClass().getSimpleName());
//            code = servletExceptionEnum.getCode();
        } catch (IllegalArgumentException e1) {
//            log.error("class [{}] not defined in enum {}", e.getClass().getName(), ServletResponseEnum.class.getName());
        }
        if (ENV_PROD.equals(profile)) {
            // 当为生产环境, 不适合把具体的异常信息展示给用户, 比如404.
            code = ResultCode.ERROR.getCode();
            BaseException baseException = new BaseException(ResultCode.ERROR.getDesc());
            String message = baseException.getMessage();
            return new Rs(code, message);
        }
        return new Rs(code, e.getMessage());
    }


    /**
     * 参数校验异常，将校验失败的所有异常组合成一条错误信息
     *
     * @return 异常结果
     * @parame 异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Rs handleValidException(MethodArgumentNotValidException e) {
        log.error("参数绑定校验异常", e);
        return wrapperBindingResult(e.getBindingResult());
    }

    /**
     * 包装绑定异常结果
     *
     * @param bindingResult 绑定结果
     * @return 异常结果
     */
    private Rs wrapperBindingResult(BindingResult bindingResult) {
        StringBuilder msg = new StringBuilder();
        for (ObjectError error : bindingResult.getAllErrors()) {
            msg.append(", ");
            if (error instanceof FieldError) {
                msg.append(((FieldError) error).getField()).append(": ");
            }
            msg.append(error.getDefaultMessage() == null ? "" : error.getDefaultMessage());
        }
        return Rs.fail(ResultCode.API_PARAM_BIND_ERROR.getCode(), msg.substring(2));
    }

    /**
     * 未定义异常
     *
     * @return 异常结果
     * @parame 异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Rs handleException(Exception e) {
        log.error(e.getMessage(), e);
        if (ENV_PROD.equals(profile)) {
            // 当为生产环境, 不适合把具体的异常信息展示给用户, 比如数据库异常信息.
            String code = ResultCode.ERROR.getCode();
            BaseException baseException = new BaseException(ResultCode.ERROR.getDesc());
            String message = baseException.getMessage();
            return new Rs(code, message);
        }
        return new Rs(ResultCode.ERROR.getCode(), e.getMessage());
    }
}
