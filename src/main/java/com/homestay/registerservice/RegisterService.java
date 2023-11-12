package com.homestay.registerservice;

import java.time.LocalDate;

import com.homestay.contract.Contract;
import com.homestay.service.Services;
import com.homestay.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RegisterService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @NotNull(message = "customer id cannot be null")
    private User customer;
    @ManyToOne
    @JoinColumn(name = "service_id")
    @NotNull(message = "service id cannot be null")
    private Services service;
    @ManyToOne
    @JoinColumn(name = "contract_id")
    @NotNull(message = "contract id cannot be null")
    private Contract contract;
    @NotNull(message = "from date cannot be null")
    private LocalDate fromDate;
    @NotNull(message = "to date cannot be null")
    private LocalDate toDate;
    private Float totalPrice;
    private Boolean status = false;
    @ManyToOne(optional = true)
    @JoinColumn(name = "admin_id")
    private User admin;
}
