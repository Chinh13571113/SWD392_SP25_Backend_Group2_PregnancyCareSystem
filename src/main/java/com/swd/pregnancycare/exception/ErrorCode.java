package com.swd.pregnancycare.exception;

public enum ErrorCode {
  USER_NOT_FOUND(400, "User not found"),
  SAVED_SUCCESSFULLY(200, "Created successfully")
  ;
  private int code;
  private String message;

  ErrorCode(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
