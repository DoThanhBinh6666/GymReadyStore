package com.project.shopapp_backend.service;

import com.project.shopapp_backend.dtos.UserDTO;
import com.project.shopapp_backend.exceptions.DataNotFoundException;
import com.project.shopapp_backend.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException;
    String login(String phoneNumber, String password);
}
