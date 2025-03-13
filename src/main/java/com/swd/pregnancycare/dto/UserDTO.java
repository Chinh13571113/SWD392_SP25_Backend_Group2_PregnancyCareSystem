package com.swd.pregnancycare.dto;

import com.swd.pregnancycare.entity.RoleEntity;
import lombok.Data;



@Data
public class UserDTO {
    private int id;
    private String email;
    private String fullName;
    private String roles;
    private boolean status;

}
