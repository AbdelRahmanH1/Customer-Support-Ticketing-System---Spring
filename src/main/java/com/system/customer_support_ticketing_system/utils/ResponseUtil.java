package com.system.customer_support_ticketing_system.utils;

import com.system.customer_support_ticketing_system.dtos.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static <T> ResponseEntity<BaseResponse<T>> success(T data){
        return ResponseEntity.ok(new BaseResponse<>(true,null,data));
    }

    public static <T> ResponseEntity<BaseResponse<T>> success(String message,T data){
        return ResponseEntity.ok(new BaseResponse<>(true,message,data));
    }

    public static <T> ResponseEntity<BaseResponse<T>> success(String message, T data, HttpStatus status){
        return ResponseEntity.status(status).body(new BaseResponse<>(true,message,data));
    }


}
