package com.swd.pregnancycare.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadException extends RuntimeException {
    private String message;
}
