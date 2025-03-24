package com.swd.pregnancycare.services;

import com.swd.pregnancycare.request.UserEditRequest;
import com.swd.pregnancycare.request.UserRequest;

import java.util.Map;

public interface AdminServices {
    Map<String,?> getDashboard();
    void addUser(UserRequest userRequest);
    void editUser(UserEditRequest userEditRequest);
}
