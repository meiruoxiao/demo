package com.mrx.www.common;

import com.mrx.www.common.exception.CommonException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * .
 *
 * @author Mei Ruoxiao
 * @since 2020/05/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rs<T> implements Serializable {

    private String message;
    private String code;
    private T data;
    private boolean result;
    private long timestamp = System.currentTimeMillis();

    public Rs(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <R> Rs<R> of(final boolean result) {
        Rs<R> commonResponse = new Rs<>();
        commonResponse.setResult(result);
        commonResponse.setData(null);
        return commonResponse;
    }

    @SuppressWarnings("unchecked")
    public static <T, R> Rs<R> of(final T data) {
        Rs<R> commonResponse = new Rs<>();
        commonResponse.setData((R) data);
        return commonResponse;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public static <T> Rs<T> of(final ResultCode resultCode) {
        Rs<T> commonResponse = new Rs<>();
        // 如果数据为null,处理为空json.暂时不启用
        if (Objects.isNull(resultCode)) {
        } else {
            commonResponse.setCode(resultCode.code());
            commonResponse.setMessage(resultCode.desc());
        }
        return commonResponse;
    }

    @SuppressWarnings("unchecked")
    public static <T, R> Rs<R> of(final boolean result, final T data) {
        Rs<R> commonResponse = new Rs<>();
        commonResponse.setResult(result);
        commonResponse.setData((R) data);
        return commonResponse;
    }

    @SuppressWarnings("unchecked")
    public static <T, R> Rs<R> of(final T data, final String msg) {
        Rs<R> commonResponse = new Rs<>();
        commonResponse.setData((R) data);
        commonResponse.setMessage(msg);
        return commonResponse;
    }

    public static <R> Rs<R> of(final boolean result, final String msg) {
        Rs<R> commonResponse = new Rs<>();
        commonResponse.setResult(result);
        commonResponse.setMessage(msg);
        return commonResponse;
    }

    @SuppressWarnings("unchecked")
    public static <T, R> Rs<R> of(final boolean result, final T data, final String msg) {
        Rs<R> commonResponse = new Rs<>();
        commonResponse.setResult(result);
        commonResponse.setData((R) data);
        commonResponse.setMessage(msg);
        return commonResponse;
    }

    @SuppressWarnings("unchecked")
    public static <T, R> Rs<R> of(final boolean result, final String code, final String msg) {
        Rs<R> commonResponse = new Rs<>();
        commonResponse.setResult(result);
        commonResponse.setCode(code);
        commonResponse.setMessage(msg);
        return commonResponse;
    }


    @SuppressWarnings("unchecked")
    public static <T, R> Rs<R> of(final boolean result, final T data, final ResultCode resultCode) {
        Rs<R> commonResponse = new Rs<>();
        commonResponse.setResult(result);
        commonResponse.setData((R) data);
        commonResponse.setMessage(resultCode.desc());
        commonResponse.setCode(resultCode.code());
        return commonResponse;
    }

    public static <R> Rs<R> of(final boolean result, final ResultCode resultCode) {
        Rs<R> commonResponse = new Rs<>();
        commonResponse.setResult(result);
        commonResponse.setMessage(resultCode.desc());
        commonResponse.setCode(resultCode.code());
        return commonResponse;
    }

    /**
     * 默认执行成功构造器.
     */
    public static <R> ResponseEntity<Rs<R>> ok() {
        return ResponseEntity.ok(Rs.of(true));
    }

    public static <T, R> ResponseEntity<Rs<R>> ok(final T data) {
        return ResponseEntity.ok(Rs.of(data));
    }

    public static <R> ResponseEntity<Rs<R>> ok(final ResultCode data) {
        return ResponseEntity.ok(Rs.of(data));
    }

    public static <T, R> ResponseEntity<Rs<R>> ok(final T data, final ResultCode resultCode) {
        return ResponseEntity.ok(Rs.of(true, data, resultCode));
    }

    public static <T, R> ResponseEntity<Rs<R>> ok(final T data, final String msg) {
        return ResponseEntity.ok(Rs.of(data, msg));
    }


    /**
     * 成功返回空的l{@link List}对象.
     */
    public static <R> ResponseEntity<Rs<List<R>>> emptyList() {
        return ResponseEntity.ok(Rs.of(Collections.emptyList()));
    }

    /**
     * 默认执行失败构造器.
     */
    public static <R> ResponseEntity<Rs<R>> fail() {
        return ResponseEntity.ok(Rs.of(false, ResultCode.ERROR));
    }

    public static <R> ResponseEntity<Rs<R>> fail(final ResultCode data) {
        return ResponseEntity.ok(Rs.of(false, data));
    }

    public static <R> ResponseEntity<Rs<R>> fail(final String msg) {
        return ResponseEntity.ok(Rs.of(false, ResultCode.of(ResultCode.ERROR.code(), msg)));
    }

    public static <T> ResponseEntity<Rs<T>> fail(final String msg, final T data) {
        return ResponseEntity.ok(Rs.of(false, data, msg));
    }

    public static <T> Rs<T> fail(final String code, final String desc) {
        return Rs.of(false, code, desc);
    }

    /**
     * 无访问权限,拒绝访问.
     */
    public static <T> ResponseEntity<Rs<T>> forbidden() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Rs.of(false, ResultCode.API_FORBIDDEN));
    }

    /**
     * 凭证过期.
     */
    public static ResponseEntity<Rs<Object>> secretExpire() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Rs.of(false, ResultCode.API_CERT_SECRET_EXPIRE));
    }

    /**
     * 凭证为空.
     */
    public static ResponseEntity<Rs<Object>> secretEmpty() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Rs.of(false, ResultCode.API_CERT_SECRET_EMPTY));
    }

    /**
     * 未认证.
     */
    public static ResponseEntity<Rs<Object>> unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Rs.of(false, ResultCode.API_UNAUTHORIZED));
    }

    /**
     * 操作不允许.
     */
    public static ResponseEntity<Rs<Object>> notAllowed(final String msg) {
        return ResponseEntity.ok(Rs.of(false, ResultCode.API_NOT_ALLOWED.desc(msg)));
    }

    /**
     * 参数格式不正确.
     */
    public static <R> ResponseEntity<Rs<R>> wrongFormat(final String field) {
        return ResponseEntity.ok(Rs.of(false, ResultCode.API_PARAM_WRONG_FORMAT.desc(field)));
    }

    /**
     * 参数值不正确.
     */
    public static <R> ResponseEntity<Rs<R>> wrongValue(final String field) {
        return ResponseEntity.ok(Rs.of(false, ResultCode.API_PARAM_WRONG_VALUE.desc(field)));
    }

    /**
     * 参数值不能为空
     */
    public static <R> ResponseEntity<Rs<R>> canNotBlank(final String field) {
        return ResponseEntity.ok(Rs.of(false, ResultCode.API_PARAM_CAN_NOT_BLANK.desc(field)));
    }

    /**
     * 参数值未传递
     */
    public static <R> ResponseEntity<Rs<R>> notPresent(final String field) {
        return ResponseEntity.ok(Rs.of(false, ResultCode.API_PARAM_NOT_PRESENT.desc(field)));
    }

    /**
     * 资源不可用
     */
    public static <R> ResponseEntity<Rs<R>> notFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Rs.of(false, ResultCode.API_NOT_FOUND));
    }

    /**
     * 请求Content-type不支持
     */
    public static <R> ResponseEntity<Rs<R>> notSupportContentType(final String contentType) {
        return ResponseEntity.ok(
                Rs.of(false, ResultCode.API_CONTENT_TYPE_NOT_SUPPORT.desc(contentType)));
    }

    /**
     * 请求方式不支持
     */
    public static <R> ResponseEntity<Rs<R>> notSupportMethod(final String method) {
        return ResponseEntity.ok(Rs.of(false, ResultCode.API_METHOD_NOT_SUPPORT.desc(method)));
    }

    /**
     * 返回结果过多
     */
    public static <R> ResponseEntity<Rs<R>> tooManyResults(final String method) {
        return ResponseEntity.ok(Rs.of(false, ResultCode.API_TOO_MANY_RESULTS.desc(method)));
    }

    /**
     * 参数值无效.
     */
    public static <R> ResponseEntity<Rs<R>> invalid(final String field) {
        return ResponseEntity.ok(Rs.of(false, ResultCode.API_PARAM_INVALID.desc(field)));
    }

    /**
     * 数据已存在.
     */
    public static <R> ResponseEntity<Rs<R>> exist(final String data) {
        return ResponseEntity.ok(Rs.of(false, ResultCode.API_DATA_EXIST.desc(data)));
    }

    /**
     * 数据不存在.
     */
    public static <R> ResponseEntity<Rs<R>> notExist(final String data) {
        return ResponseEntity.ok(Rs.of(false, ResultCode.API_DATA_NOT_EXIST.desc(data)));
    }

    /**
     * 参数绑定有误.
     */
    public static <R> ResponseEntity<Rs<R>> bindError(final String msg) {
        return ResponseEntity.ok(Rs.of(false, ResultCode.API_PARAM_BIND_ERROR.desc(msg)));
    }

    /**
     * 接口调用判断,如果出现网络异常(如中断),抛出{@link CommonException#of(ResultCode)}.
     *
     * @param responseEntity 接口返回
     * @return 请求数据实体
     * @see #requireNonNull(ResponseEntity, ResultCode)
     */
    public static <R> R requireNonNull(ResponseEntity<Rs<R>> responseEntity) {
        return requireNonNull(responseEntity, ResultCode.ERROR);
    }

    /**
     * 接口调用判断.
     *
     * @param responseEntity REST接口响应
     * @param resultCode     返回该结果码,当满足以下任一条件时:<br>
     *                       1. <code>responseEntity</code>为空<br>
     *                       2. <code>responseEntity.body</code>为空<br>
     * @return Rs内的对象
     */
    public static <R> R requireNonNull(
            final ResponseEntity<Rs<R>> responseEntity, final ResultCode resultCode) {
        if (Objects.isNull(responseEntity) || !responseEntity.hasBody()) {
            throw CommonException.of(resultCode);
        }
        final Rs<R> body = responseEntity.getBody();
        if (!Objects.requireNonNull(body, resultCode.desc).isResult()) {
            throw CommonException.of(ResultCode.of(body.code, body.message));
        }
        return body.data;
    }

    /**
     * 判断ResultCode是否=SUCCESS <br>
     * null也会返回true
     */
    public static boolean isSuccess(final ResultCode resultCode) {
        return Objects.isNull(resultCode) || Objects.equals(resultCode.code, ResultCode.SUCCESS.code);
    }

    /**
     * 判断ResultCode是否=ERROR <br>
     * null返回false
     */
    public static boolean isError(final ResultCode resultCode) {
        return Objects.nonNull(resultCode) && Objects.equals(resultCode.code, ResultCode.ERROR.code);
    }
}
