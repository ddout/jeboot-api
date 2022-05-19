package org.jeecg.modules.otheraccount.util.zentao.domain;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author xiaodizi
 */
public class JsonResult<T> {
    private int code;
    private String message;
    private T data;

    public JsonResult() {
    }

    public JsonResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public JsonResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <T> JsonResult<T> success(T data) {
        return new JsonResult<>(200, "成功", data);
    }


    @Override
    public String toString() {
        return "JsonResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
