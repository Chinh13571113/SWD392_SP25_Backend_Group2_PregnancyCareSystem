package com.swd.pregrancycare.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadException extends RuntimeException {
    private String message;
}
