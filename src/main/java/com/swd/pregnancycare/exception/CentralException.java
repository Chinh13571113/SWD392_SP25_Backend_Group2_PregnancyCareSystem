package com.swd.pregnancycare.exception;

import com.swd.pregnancycare.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CentralException {
    @ExceptionHandler({InsertException.class})
    public ResponseEntity<?> centralLog(Exception e){
        return ResponseEntity.internalServerError().body("error");
    }
    @ExceptionHandler({FileUploadException.class})
    public ResponseEntity<?> centralFileUpload(Exception e){
        return ResponseEntity.badRequest().body("error");
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
