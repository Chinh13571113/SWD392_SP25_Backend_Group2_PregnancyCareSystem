package com.swd.pregnancycare.dto;

import com.swd.pregnancycare.entity.RoleEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {

     int id;
     String email;
     String fullName;
     String roles;

     boolean status;


}
