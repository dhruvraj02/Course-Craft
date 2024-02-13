package com.project.CourseCraft.exception;

import com.project.CourseCraft.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception handler for the ResourceNotFoundException.
     * Handles the ResourceNotFoundException and returns a ResponseEntity with an appropriate ApiResponse and HTTP status.
     *
     * @param e The ResourceNotFoundException instance.
     * @return ResponseEntity with ApiResponse and HTTP status.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException e){
        String message = e.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);

        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler for the MethodArgumentNotValidException.
     * Handles the MethodArgumentNotValidException and returns a ResponseEntity with a map of field errors and HTTP status.
     *
     * @param e The MethodArgumentNotValidException instance.
     * @return ResponseEntity with a map of field errors and HTTP status.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> methodArgsNotValidHandler(MethodArgumentNotValidException e){
        Map<String,String> resp = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(
                (error) -> {
                    String fieldName = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    resp.put(fieldName,message);
                });
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for the ApiException.
     * Handles the ApiException and returns a ResponseEntity with an appropriate ApiResponse and HTTP status.
     *
     * @param e The ApiException instance.
     * @return ResponseEntity with ApiResponse and HTTP status.
     */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> apiExceptionHandler(ApiException e){
        String message = e.getMessage();
        ApiResponse apiResponse = new ApiResponse(message, false);

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

}
