package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.UserDTO;
import com.swd.pregnancycare.entity.RoleEntity;
import com.swd.pregnancycare.entity.UserEntity;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.repository.RoleRepo;
import com.swd.pregnancycare.repository.UserRepo;
import com.swd.pregnancycare.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServicesImp implements UserServices{

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getListUser() {


        return userRepo.findAll().stream().map(data->{
            UserDTO userDTO = new UserDTO();
            userDTO.setId(data.getId());
            userDTO.setEmail(data.getEmail());
            userDTO.setFullName(data.getFullName());
            userDTO.setRoles(data.getRole().getName());
            return userDTO;
        }).toList();
    }

    @Override
    public Boolean deleteUserById(int id) {
        try {
            userRepo.deleteById(id);
        }catch (Exception e){
            System.out.println(e);
        }
        return true;
    }

    @Override
    public Boolean createUser(UserRequest request) {
        if(userRepo.existsByEmail(request.getEmail())) throw new AppException(ErrorCode.USER_EXIST);
        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Mã hóa mật khẩu
        user.setFullName(request.getFullName());
        setDefaultRole(user);
        userRepo.save(user);
        return true;

    }
    private void setDefaultRole(UserEntity user) {
        if (user.getRole() == null) {
            // Truy vấn RoleEntity với tên "ROLE_USER"
            RoleEntity defaultRole = roleRepo.findByName("ROLE_MEMBER")
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));  // Nếu không tìm thấy role "ROLE_USER"
            user.setRole(defaultRole);
        }
    }
}
