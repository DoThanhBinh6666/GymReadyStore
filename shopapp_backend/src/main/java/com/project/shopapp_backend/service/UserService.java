package com.project.shopapp_backend.service;

import com.project.shopapp_backend.dtos.UserDTO;
import com.project.shopapp_backend.exceptions.DataNotFoundException;
import com.project.shopapp_backend.models.Role;
import com.project.shopapp_backend.models.User;
import com.project.shopapp_backend.repositories.RoleRepsitory;
import com.project.shopapp_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private RoleRepsitory roleRepsitory;
    private UserRepository userRepository;
    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        String phoneNumber = userDTO.getPhoneNumber();
        // kiểm tra xem số điện thoại đã tồn tại hay chưa
        if(userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("so dien thoai da ton tai");
        }
        //convert from userDTO => user
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBrith())
                .facebookAccountId(String.valueOf(userDTO.getFacebookAccountId()))
                .googleAccountId(String.valueOf(userDTO.getGoogleAccountId()))
                .build();
        Role role =roleRepsitory.findById(userDTO.getRoleId()).orElseThrow(()->new DataNotFoundException("role not found"));
        newUser.setRole(role);
        //kiem tra neu co accountId, khong yeu cau password
        if(userDTO.getFacebookAccountId()==0 && userDTO.getGoogleAccountId()==0) {
            String password = userDTO.getPassword();
            //String encodePassword = paswordEncoder.encode(password);
            //newUser.setPassword(encodePassword);
        }
        return userRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) {
        //lam sau
        return null;
    }
}
