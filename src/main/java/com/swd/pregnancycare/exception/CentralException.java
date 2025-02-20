package com.swd.pregnancycare.exception;

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
}
