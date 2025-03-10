package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.UserDTO;
import com.swd.pregnancycare.request.UserRequest;


import java.util.List;

public interface UserServices {
    List<UserDTO> getListUser();
    Boolean deleteUserById(int id);
    Boolean createUser(UserRequest request);
}
