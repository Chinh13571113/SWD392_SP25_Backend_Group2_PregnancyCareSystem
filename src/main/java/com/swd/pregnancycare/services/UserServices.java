package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.UserDTO;
import com.swd.pregnancycare.request.UserRequest;
import com.swd.pregnancycare.response.ExpertResponse;
import com.swd.pregnancycare.response.UserResponse;
import org.springframework.security.access.prepost.PreAuthorize;


import java.util.List;

public interface UserServices {
    List<UserDTO> getListUser();
    Boolean deleteUserById(int id);
    Boolean createUser(UserRequest request);
    UserResponse getMyInfo();
    void updateUser(int id, String fullName, String email);
    Boolean forgotPassword(String email);
    void changePassword(String oldPassword, String newPassword);

  ExpertResponse getExpertDetail(int expertId);

  List<ExpertResponse> getAllExperts();
}
