package com.homestay.contract;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.homestay.room.Room;

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

    
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Contract>> getContractByUser_Id(@PathVariable Integer id) {
        
        return ResponseEntity.status(HttpStatus.OK).body(contractService.getContractByUser_Id(id));
    }
    @GetMapping("")
    public ResponseEntity<List<Contract>> getAllContract() {
        return ResponseEntity.status(HttpStatus.OK).body(contractService.getAllContract());
    }
    @PatchMapping("{id}")
    public ResponseEntity<List<Contract>> updateContract(@PathVariable("id") Integer contractId,@RequestBody ContractUpdateDTO contractUpdateDTO) {
        contractService.updateContract(contractId,contractUpdateDTO);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("{id}")
    public ResponseEntity<List<Contract>> deleteContract(@PathVariable("id") Integer contractId) {
        contractService.deleteContract(contractId);
        return ResponseEntity.noContent().build();
    }
}
