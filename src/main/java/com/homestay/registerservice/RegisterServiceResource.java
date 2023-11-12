package com.homestay.registerservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;
@RestController
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4401"})
@RequestMapping("api/v1/register-service")
public class RegisterServiceResource {
    @Autowired
    private RegisterServiceService service;
    @PostMapping()
    public ResponseEntity<Void> registerService(@Valid @RequestBody RegisterService registerService) {
        service.registerService(registerService);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("{id}")
    public ResponseEntity<List<RegisterServiceResponseDTO>> getByUserId(@PathVariable("id") Integer userId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getByUserId(userId));
    }
    @GetMapping()
    public ResponseEntity<List<RegisterServiceResponseDTO>> getAllRegister() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllRegister());
    }
    
}
