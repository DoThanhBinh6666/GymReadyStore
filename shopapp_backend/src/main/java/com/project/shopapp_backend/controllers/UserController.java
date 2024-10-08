package com.project.shopapp_backend.controllers;

import com.project.shopapp_backend.dtos.UserDTO;
import com.project.shopapp_backend.dtos.UserLoginDTO;
import com.project.shopapp_backend.service.IUserService;
import com.project.shopapp_backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO,
                                        BindingResult result)
    {
        try
        {
            if (result.hasErrors())
            {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(f -> f.getDefaultMessage())
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
             return ResponseEntity.badRequest().body("Password khong trung");
            }
            userService.createUser(userDTO);
            return ResponseEntity.ok("register thanh cong!");
        }
        catch(Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO userLoginDTO)
    {
        // kiem tra thong tin dang nhap va sinh token
        String token = userService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
        // tra ve token trong Response
        return ResponseEntity.ok(token);
    }
}
