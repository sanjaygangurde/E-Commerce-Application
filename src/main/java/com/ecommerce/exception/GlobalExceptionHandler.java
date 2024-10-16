package com.ecommerce.exception;

import com.ecommerce.payloads.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {

        logger.info("Exception Handler Invoked");

        ApiResponse build = ApiResponse.builder().message(exception.getMessage()).success(true).status(HttpStatus.NOT_FOUND).build();

        return new ResponseEntity<>(build, HttpStatus.NOT_FOUND);

    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {

        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

        Map<String, Object> response = new HashMap<>();

        allErrors.stream().forEach(objectError -> {
            String fieldName = ((FieldError) objectError).getField();
            String errorMessage = objectError.getDefaultMessage();
            response.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(BadApiRequestException.class)
    public ResponseEntity<ApiResponse> badApiRequestHandler(BadApiRequestException exception) {

        logger.info("Bad Api Request Handler Invoked");

        ApiResponse build = ApiResponse.builder().message(exception.getMessage()).success(false).status(HttpStatus.BAD_REQUEST).build();

        return new ResponseEntity<>(build, HttpStatus.BAD_REQUEST);

    }
}
