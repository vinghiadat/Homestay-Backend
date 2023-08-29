package com.HomestayManagementSystem.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.HomestayManagementSystem.message.SuccessMessage;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1")
public class UserResource {
    
    private UserService userService;

    @Autowired //tiêm phụ thuộc vào
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("register")
    public ResponseEntity<SuccessMessage> register( @Valid @RequestBody User user) {
        userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessMessage("Account successfully created", HttpStatus.CREATED));
    }

}
