package com.swd.pregnancycare.services;

import com.swd.pregnancycare.dto.UserDTO;
import com.swd.pregnancycare.entity.UserEntity;
import com.swd.pregnancycare.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServicesImp implements UserServices{
    @Autowired
    private UserRepo userRepo;
    @Override
    public List<UserDTO> getListUser() {

        return userRepo.findAll().stream().map(data->{
            UserDTO userDTO = new UserDTO();
            userDTO.setId(data.getId());
            userDTO.setEmail(data.getEmail());
            userDTO.setFullName(data.getFullName());
            return userDTO;
        }).toList();
    }
}
