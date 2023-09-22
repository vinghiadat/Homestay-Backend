package com.homestay.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import com.homestay.role.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // Tạo bảng trong CSDL
@Table(name = "users") // tên bảng được đặt là users vì user trùng với CSDL
public class User {

    @Id // Đánh dấu đây là ID
    @GeneratedValue(strategy = GenerationType.IDENTITY) // trường tăng tự động
    private Integer id;

    @NotEmpty(message = "Username không được rỗng")
    private String username; // phone number
    @NotEmpty(message = "Mật khẩu không được rỗng")
    private String password;
    @NotEmpty(message = "Số điện thoại không được rỗng")
    private String phone;
    @NotEmpty(message = "Email không được rỗng")
    @Email(message = "Email không hợp lệ")
    private String email;
    @NotEmpty(message = "Tên không được rỗng")
    private String name;
    @NotNull(message = "Giới tính không được null")
    private Integer gender;
    @NotNull(message = "Ngày sinh không được null")
    private LocalDate birthday;
    


    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL) //EAGER tải toàn bộ dữ liệu cùng lúc của bảng khóa ngoại, CascadeType.ALL lan truyền sự kiện giữa Parent Table and Child Table
    @JoinTable(name = "user_role"
        ,joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id") //Đem cột id trong bảng user vào thành user_id
        ,inverseJoinColumns  = @JoinColumn(name = "role_id",referencedColumnName = "id") //Đem cột id trong bảng Role vào thành role_id
    )
    private List<Role> roles = new ArrayList<>();

}
