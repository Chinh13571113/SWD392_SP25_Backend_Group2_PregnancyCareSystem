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

    USER_EXIST(400, "user existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXIST(403, "user not existed", HttpStatus.BAD_REQUEST),
    USER_NOT_ACTIVE(403, "user is not  active", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(1001, "uncategorize exception", HttpStatus.INTERNAL_SERVER_ERROR),
    UPDATE_USER_FAILED(400, "update user failed", HttpStatus.BAD_REQUEST),
    BLOG_SAVED_EXCEPTION(1002, "blog save error", HttpStatus.BAD_REQUEST),
    BLOG_NOT_TRASHED(400, "blog is activating", HttpStatus.BAD_REQUEST),
    BLOG_NOT_DELETED(400, "blog is not deleted", HttpStatus.BAD_REQUEST),
    BLOG_IS_NOT_ARTICLE(400, "blog is not article", HttpStatus.BAD_REQUEST),
    BLOG_IS_NOT_POST(400, "blog is not post", HttpStatus.BAD_REQUEST),
    POST_IS_NOT_APPROVED(400, "post is not approved", HttpStatus.BAD_REQUEST),
    ARTICLE_IS_NOT_APPROVED(400, "article is not approved", HttpStatus.BAD_REQUEST),
    BLOG_DELETED_FAILED(1002, "delete blog error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_PASSWORD(400, "password must be at least 8", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED_EXCEPTION(999, "do not permission", HttpStatus.FORBIDDEN),
    PASSWORD_NOT_CORRECT(400, "password is not correct", HttpStatus.BAD_REQUEST),


    ROLE_NOT_FOUND(401, "Role does not exist", HttpStatus.NOT_FOUND),
    REGISTER_FAILED(400, "register failed", HttpStatus.BAD_REQUEST),
    BLOG_NOT_EXIST(404, "blog not found", HttpStatus.NOT_FOUND),
    ARTICLE_NOT_EXIST(404, "article not found", HttpStatus.NOT_FOUND),
    GROUP_NOT_EXIST(404, "group not existed", HttpStatus.NOT_FOUND),
    GROUP_EXIST(400, "group existed", HttpStatus.BAD_REQUEST),
    GROUP_HAS_USER_ALREADY(999, "group has a user already", HttpStatus.CONFLICT),
    FETUS_NOT_EXIST(403, "fetus not existed", HttpStatus.BAD_REQUEST),


    ADVICE_SAVED_EXCEPTION(500, "save advice error", HttpStatus.INTERNAL_SERVER_ERROR),
    ADVICE_NOT_EXIST(404, "advice not existed", HttpStatus.NOT_FOUND),
    ADVICE_DELETE_FAILED(500, "delete advice error", HttpStatus.INTERNAL_SERVER_ERROR),
    ADVICE_UPDATE_FAILED(500, "update advice error", HttpStatus.INTERNAL_SERVER_ERROR),


    BLOG_CATEGORY_EXIST(400, "blog category existed", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_EXIST(404, "category not existed", HttpStatus.NOT_FOUND),
    BLOG_CATEGORY_DELETE_FAILED(500, "delete blog category error", HttpStatus.INTERNAL_SERVER_ERROR),
    BLOG_CATEGORY_UPDATE_FAILED(500, "update blog category error", HttpStatus.INTERNAL_SERVER_ERROR),


    BLOG_COMMENT_SAVED_EXCEPTION(500, "save blog comment error", HttpStatus.INTERNAL_SERVER_ERROR),
    BLOG_COMMENT_NOT_EXIST(404, "blog comment not existed", HttpStatus.NOT_FOUND),


    RECORD_NOT_EXIST(404, "record does not exist", HttpStatus.NOT_FOUND),
    DATA_NOT_FOUND(404, "Data not found", HttpStatus.NOT_FOUND), APPOINTMENT_NOT_EXIST(404, "appointment not exist", HttpStatus.NOT_FOUND),
    SCHEDULE_NOT_EXIST(404, "Cannot find Schedule", HttpStatus.NOT_FOUND),
    APPOINTMENT_EXISTED(403, "Your appointment is already created", HttpStatus.BAD_REQUEST),
    SCHEDULE_EXISTED(403, "Your Schedule is not in part of times", HttpStatus.BAD_REQUEST),
    AVAILABLE_WRITER(403, "Appointment with the same date and user already exists.", HttpStatus.BAD_REQUEST), SCHEDULE_INVALID(403,"Date not in the past" ,HttpStatus.BAD_REQUEST );







    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

}
