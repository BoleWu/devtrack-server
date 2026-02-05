package com.devtrack.common.result;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class R<T> {
    private int code;
    private String message;
    private long time;
    private T responseBody;

    public R(int code, String message, T responseBody) {
        this.code = code;
        this.message = message;
        this.responseBody = responseBody;
        this.time = System.currentTimeMillis();
    }

    public static <T> R<T> success(T data){
        return new R<>(0, "success", data);
    }

    public static <T> R<T> fail(String message, T data){
        return new R<>(200, message, data);
    }
    public static R<?> error(String message){
        return new R<>(404, message, null);
    }

}