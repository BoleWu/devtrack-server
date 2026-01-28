package com.devtrack.common.result;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class R<T> {
    private int errorCode;
    private String message;
    private long time;
    private T responseBody;

    public R(int errorCode, String message, T responseBody) {
        this.errorCode = errorCode;
        this.message = message;
        this.responseBody = responseBody;
        this.time = System.currentTimeMillis();
    }

    public static <T> R<T> ok(T data){
        return new R<>(200, "success", data);
    }

    public static <T> R<T> fail(String message){
        return new R<>(200, message, null);
    }
    public static R<?> error(String message){
        return new R<>(404, message, null);
    }

}