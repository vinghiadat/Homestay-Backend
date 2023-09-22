package com.homestay.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    @NotEmpty(message = "Username không được rỗng")
    private String username;
    @NotEmpty(message = "Mật khẩu không được rỗng")
    private String password;
}
