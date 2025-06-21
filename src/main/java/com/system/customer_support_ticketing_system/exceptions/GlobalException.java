package com.system.customer_support_ticketing_system.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoHandlerFoundException(NoHandlerFoundException e) {
        return  buildResponse("Page not found",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity<Map<String, Object>> handleForbiddenException(NoHandlerFoundException e) {
        return  buildResponse("No access",HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<Map<String, Object>> buildResponse(String message,HttpStatus status) {
        Map<String,Object> body = new HashMap<>();
        body.put("success",false);
        body.put("message",message);
        return  ResponseEntity.status(status).body(body);
    }

}
