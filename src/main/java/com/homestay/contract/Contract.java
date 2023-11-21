package com.homestay.contract;

import java.time.LocalDate;
import com.homestay.room.Room;
import com.homestay.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "roomtype cannot be blank")
    private String roomType;
    @NotNull(message = "number room cannot be null")
    private Integer numberRoom;
    private Float totalPrice;
    private LocalDate createdDate = LocalDate.now(); //ngày booking
    @NotNull(message = "check in date cannot be null")
    private LocalDate checkinDate; //ngày đến
    @NotNull(message = "check out date cannot be null")
    private LocalDate checkoutDate; //ngày đi
    @NotNull(message = "customer_id cannot be null")
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;//Khách hàng
    @ManyToOne(optional = true)
    @JoinColumn(name = "admin_id")
    private User admin;
    private Integer status = 0;
    private String note = new String();
}