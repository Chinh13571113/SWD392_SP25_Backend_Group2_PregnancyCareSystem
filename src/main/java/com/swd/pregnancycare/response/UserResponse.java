package com.swd.pregnancycare.response;

import lombok.Data;

@Data
public class UserResponse {
    private int id;
    private String email;
    private String fullName;
    private String roleName;
    private boolean status;
}
