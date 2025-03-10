package com.swd.pregnancycare.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ErrorCode {
    USER_EXIST(400,"user existed"),
    UNCATEGORIZED_EXCEPTION(999,"uncategorize exception"),
    BLOG_SAVED_EXCEPTION(999,"blog save error")
    ;

    private int code;
    private String message;

}
