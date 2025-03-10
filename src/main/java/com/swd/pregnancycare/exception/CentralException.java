package com.swd.pregnancycare.exception;

import com.swd.pregnancycare.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class CentralException {
    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> centralLog(Exception e){
        BaseResponse response = new BaseResponse();

        response.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        response.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        return ResponseEntity.internalServerError().body(response);
    }
    @ExceptionHandler({InsertException.class})
    public ResponseEntity<?> centralLog(InsertException e){
        BaseResponse response = new BaseResponse();
        ErrorCode errorCode= e.getErrorCode();
        response.setMessage(e.getMessage());
        response.setCode(e.hashCode());
        return ResponseEntity.internalServerError().body(response);
    }
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException e){
        BaseResponse response = new BaseResponse();

        response.setMessage(Objects.requireNonNull(e.getFieldError()).getDefaultMessage());
        response.setCode(400);
        return ResponseEntity.internalServerError().body(response);
    }
    @ExceptionHandler({FileUploadException.class})
    public ResponseEntity<?> centralFileUpload(Exception e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({AppException.class})
    public ResponseEntity<?> centralApp(AppException e){
        ErrorCode errorCode = e.getErrorCode();
        BaseResponse baseResponse = new BaseResponse();

        baseResponse.setCode(errorCode.getCode());
        baseResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(baseResponse);
    }


}
