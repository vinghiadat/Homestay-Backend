package com.homestay.contract;


import com.homestay.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractUpdateDTO {
    private User admin;
    private Integer status;
    private String note;
}
