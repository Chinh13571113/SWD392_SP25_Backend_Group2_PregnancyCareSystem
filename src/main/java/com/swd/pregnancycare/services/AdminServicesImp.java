package com.swd.pregnancycare.services;

import com.swd.pregnancycare.entity.UserEntity;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.repository.BlogRepo;
import com.swd.pregnancycare.repository.UserRepo;
import com.swd.pregnancycare.request.UserEditRequest;
import com.swd.pregnancycare.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminServicesImp implements AdminServices {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BlogRepo blogRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserServicesImp userServicesImp;

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public Map<String, ?> getDashboard() {
        Map<String, Object> dashboardData = new HashMap<>();
        long totalUsers = userRepo.countActiveUsers();
        long totalBlogs = blogRepo.count();
        dashboardData.put("totalUsers", totalUsers);
        dashboardData.put("totalBlogs", totalBlogs);
        return dashboardData;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void addUser(UserRequest request) {
        if(userRepo.existsByEmailAndStatusTrue(request.getEmail())) throw new AppException(ErrorCode.USER_EXIST);
        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Mã hóa mật khẩu
        user.setFullName(request.getFullName());
        user.setStatus(true);
        userServicesImp.setDefaultRole(user,request.getRole());
        userRepo.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void editUser(UserEditRequest userEditRequest) {
      UserEntity user =  userRepo.findById(userEditRequest.getId()).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXIST));
        user.setStatus(userEditRequest.isStatus());
        userServicesImp.setUpRole(user,userEditRequest.getRole());
        userRepo.save(user);
    }

}
