package com.homestay.user;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.Collections;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.homestay.config.JwtGenerator;
import com.homestay.exception.AlreadyExistsException;
import com.homestay.exception.InvalidValueException;
import com.homestay.exception.NotFoundException;
import com.homestay.role.Role;
import com.homestay.role.RoleRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    private AuthenticationManager authenticationManager;

    private JwtGenerator jwtGenerator;

    @Autowired //tiêm phụ thuộc vào
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository,
            AuthenticationManager authenticationManager, JwtGenerator jwtGenerator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
    }
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NotFoundException("Không tồn tại user này"));
        return user;
    }
    @Transactional //Đánh dấu là 1 giao dịch, nếu có vấn đề nó rollback hết
    public void register(User user) {

        // Nếu tài khoản tồn tại thì ném ra exception
        if(userRepository.existsByUsername(user.getUsername())) {
            throw new AlreadyExistsException("Tài khoản đã tồn tại rồi");
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

    public String login(UserLoginDTO userLoginDTO) {

        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String token = jwtGenerator.generateToken(authentication);

        return token;
    }

    public List<String> getRoleByUsername(String username) {
        if(!userRepository.existsByUsername(username)) {
            throw new UsernameNotFoundException(username + " not found");
        }else { 
            return userRepository.findRoleNamesByUsername(username);
        }
    }
    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword) {
        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));

        // Check if the old password matches
        if (!passwordEncoder.matches(oldPassword, existingUser.getPassword())) {
            throw new InvalidValueException("Mật khẩu cũ không đúng");
        }

        // Set and encode the new password
        existingUser.setPassword(passwordEncoder.encode(newPassword));

        // Save the updated user with the new password
        userRepository.save(existingUser);
    }

}
