package com.homestay.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4401"})
@RequestMapping("api/v1/contract")
public class ContractResource {
    @Autowired //tiêm phụ thuộc
    private ContractService contractService;

    @PostMapping("")
    public ResponseEntity<Void> addContract(@Valid @RequestBody Contract contract) {
        contractService.addContract(contract);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
