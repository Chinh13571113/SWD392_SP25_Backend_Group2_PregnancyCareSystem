package com.swd.pregnancycare.exception;

import lombok.Data;

@Data

public class InsertException extends RuntimeException {
    private final String message;
    public InsertException(String message) {
        super(message);
        this.message = message;
    }
}
