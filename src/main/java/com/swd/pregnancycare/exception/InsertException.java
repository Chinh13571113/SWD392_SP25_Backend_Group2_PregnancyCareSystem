package com.swd.pregnancycare.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data

public class InsertException extends RuntimeException {
    private ErrorCode errorCode;

    public InsertException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode=errorCode;
    }
}
