package com.swd.pregrancycare.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class InsertException extends RuntimeException {
    private final String message;
    public InsertException(String message) {
        super(message);
        this.message = message;
    }

}
