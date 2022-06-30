package com.youjian.ggkt.result;

import lombok.Data;

// 统一返回结果类
@Data
public class Result<T> {

    private Integer code;
    private String message;
    private T data;

    public Result() {}

    public static <T> Result<T> ok() {
        return ok(null);
    }

    public static <T> Result<T> ok(T date) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("成功");
        if (date != null) {
            result.setData(date);
        }
        return result;
    }

    public static <T> Result<T> fail() {
        Result<T> result = new Result<>();
        result.setCode(201);
        result.setMessage("失败");
        return result;
    }

    public static <T> Result<T> fail(String message) {
        Result<T> result = fail();
        result.setMessage(message);
        return result;
    }

    public static <T> Result<T> fail(String message, T date) {
        Result<T> result = new Result<>();
        result.setCode(201);
        result.setMessage(message);
        if (date != null) {
            result.setData(date);
        }
        return result;
    }

    public Result<T> message(String message) {
        this.setMessage(message);
        return this;
    }

    public Result<T> code(Integer code) {
        this.setCode(code);
        return this;
    }
}
