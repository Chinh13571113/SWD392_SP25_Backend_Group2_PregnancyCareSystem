package com.swd.pregnancycare.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {

    USER_EXIST(400,"user existed",HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST(403,"user not existed",HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(1001,"uncategorize exception", HttpStatus.INTERNAL_SERVER_ERROR),
    UPDATE_USER_FAILED(400, "update user failed", HttpStatus.BAD_REQUEST),
    BLOG_SAVED_EXCEPTION(1002,"blog save error",HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(400,"password must be at least 8",HttpStatus.BAD_REQUEST),
    UNAUTHORIZED_EXCEPTION(999,"do not permission", HttpStatus.FORBIDDEN),



    ROLE_NOT_FOUND(401,"Role does not exist",HttpStatus.NOT_FOUND ),
    REGISTER_FAILED(400,"register failed",HttpStatus.BAD_REQUEST ),
    BLOG_NOT_EXIST(404,"blog not found",HttpStatus.NOT_FOUND),
    GROUP_NOT_EXIST(404, "group not existed",HttpStatus.NOT_FOUND),
    GROUP_HAS_USER_ALREADY(999, "group has a user already",HttpStatus.CONFLICT),
    FETUS_NOT_EXIST(403, "fetus not existed",HttpStatus.BAD_REQUEST),



    ADVICE_SAVED_EXCEPTION(500, "save advice error", HttpStatus.INTERNAL_SERVER_ERROR),
    ADVICE_NOT_EXIST(404, "advice not existed", HttpStatus.NOT_FOUND),
    ADVICE_DELETE_FAILED(500, "delete advice error", HttpStatus.INTERNAL_SERVER_ERROR),
    ADVICE_UPDATE_FAILED(500, "update advice error", HttpStatus.INTERNAL_SERVER_ERROR),



    RECORD_NOT_EXIST(404, "record does not exist",HttpStatus.NOT_FOUND);





    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

}
