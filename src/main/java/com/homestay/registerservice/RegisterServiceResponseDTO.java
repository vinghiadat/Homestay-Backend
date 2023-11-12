package com.homestay.registerservice;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterServiceResponseDTO {
    private Integer id;
    private String serviceName;
    private Float totalPrice;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Boolean status;
    private Integer contractId;
}
