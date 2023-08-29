package com.HomestayManagementSystem.user;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.HomestayManagementSystem.exception.UsernameAlreadyExistsException;
import com.HomestayManagementSystem.role.Role;
import com.HomestayManagementSystem.role.RoleRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    @Autowired //tiêm phụ thuộc vào
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }
    @Transactional //Đánh dấu là 1 giao dịch, nếu có vấn đề nó rollback hết
    public void register(User user) {
        // Nếu tài khoản tồn tại thì ném ra exception
        if(userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException("Account already exists", BAD_REQUEST);
        }

        //Tìm role
        Role role = roleRepository.findByName("CUSTOMER");

        // Set role

        user.setRoles(Collections.singletonList(role));
        
        // Mã hóa mật khẩu
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save Mật khẩu
        userRepository.save(user);
    }

}
