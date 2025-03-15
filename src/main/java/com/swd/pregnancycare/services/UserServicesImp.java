package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.UserDTO;
import com.swd.pregnancycare.entity.RoleEntity;
import com.swd.pregnancycare.entity.UserEntity;
import com.swd.pregnancycare.exception.AppException;
import com.swd.pregnancycare.exception.ErrorCode;
import com.swd.pregnancycare.mapper.UserMapper;
import com.swd.pregnancycare.repository.RoleRepo;
import com.swd.pregnancycare.repository.UserRepo;
import com.swd.pregnancycare.request.UserRequest;
import com.swd.pregnancycare.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServicesImp implements UserServices{

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasAnyRole( 'MEMBER', 'ADMIN')")
    public Boolean deleteUserById(int id) {
        Optional<UserEntity> user = userRepo.findByIdAndStatusTrue(id);
        if(user.isEmpty()) throw new AppException(ErrorCode.USER_NOT_EXIST);

        try {
            UserEntity userEntity = user.get();
            userEntity.setStatus(false);
            userRepo.save(userEntity);
        }catch (Exception e){
            System.out.println(e);
        }
        return true;
    }

    @Override
    @PreAuthorize("hasAnyRole( 'MEMBER', 'ADMIN')")
    public Boolean createUser(UserRequest request) {
        System.out.println("List: "+userRepo.existsByEmailAndStatusTrue(request.getEmail()));
        if(userRepo.existsByEmailAndStatusTrue(request.getEmail())) throw new AppException(ErrorCode.USER_EXIST);
        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Mã hóa mật khẩu
        user.setFullName(request.getFullName());
        user.setStatus(true);
        setDefaultRole(user);
        userRepo.save(user);
        return true;
    }

    @Override
    @PreAuthorize("hasRole('MEMBER')")
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        UserEntity user =userRepo.findByEmailAndStatusTrue(name).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXIST));
        return UserMapper.INSTANCE.toUserResponse(user);
    }

    @Override
    @PreAuthorize("hasRole('MEMBER')")
    public void updateUser(int id, String fullName, String email, String password) {
        // Update user
        if(password == null) {
            if(userRepo.existsByEmailAndStatusTrue(email) && !userRepo.findById(id).map(UserEntity::getEmail).orElse("").equals(email)) {
                throw new AppException(ErrorCode.USER_EXIST);
            }
            Optional<UserEntity> user = userRepo.findById(id);
            UserEntity newUser = user.get();
            newUser.setEmail(email);
            newUser.setFullName(fullName);
            userRepo.save(newUser);
        }
        // Change password
        else {
            UserEntity user = userRepo.findById(id).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXIST));
            user.setPassword(passwordEncoder.encode(password)); // Mã hóa mật khẩu
            userRepo.save(user);
        }
    }

    private void setDefaultRole(UserEntity user) {
        if (user.getRole() == null) {
            // Truy vấn RoleEntity với tên "ROLE_USER"
            RoleEntity defaultRole = roleRepo.findByName("MEMBER")
                    .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));  // Nếu không tìm thấy role "ROLE_USER"
            user.setRole(defaultRole);
        }
    }
}
