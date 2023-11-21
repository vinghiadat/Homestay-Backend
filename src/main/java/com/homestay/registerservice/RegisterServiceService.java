package com.homestay.registerservice;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homestay.contract.Contract;
import com.homestay.contract.ContractRepository;
import com.homestay.exception.InvalidValueException;
import com.homestay.exception.NotFoundException;
import com.homestay.service.Services;
import com.homestay.service.ServicesRepository;
import com.homestay.user.User;
import com.homestay.user.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class RegisterServiceService {
    private RegisterServiceRepository registerServiceRepository;
    private UserRepository userRepository;
    private ServicesRepository servicesRepository;
    private ContractRepository contractRepository;
    @Autowired
    public RegisterServiceService(RegisterServiceRepository registerServiceRepository, UserRepository userRepository,
            ServicesRepository servicesRepository, ContractRepository contractRepository) {
        this.registerServiceRepository = registerServiceRepository;
        this.userRepository = userRepository;
        this.servicesRepository = servicesRepository;
        this.contractRepository = contractRepository;
    }
    public List<RegisterServiceResponseDTO> getByUserId(Integer userId) {
        List<RegisterService> registerServices = registerServiceRepository.findByCustomerId(userId);
        List<RegisterServiceResponseDTO> registerServiceResponseDTOs = new ArrayList<>();
        for(RegisterService r: registerServices) {
            RegisterServiceResponseDTO registerServiceResponseDTO = new RegisterServiceResponseDTO();
            registerServiceResponseDTO.setContractId(r.getContract().getId());
            registerServiceResponseDTO.setId(r.getId());
            registerServiceResponseDTO.setFromDate(r.getFromDate());
            registerServiceResponseDTO.setServiceName(r.getService().getName());
            registerServiceResponseDTO.setStatus(r.getStatus());
            registerServiceResponseDTO.setToDate(r.getToDate());
            registerServiceResponseDTO.setTotalPrice(r.getTotalPrice());
            registerServiceResponseDTOs.add(registerServiceResponseDTO);
        }
        return registerServiceResponseDTOs;
    }
    private boolean checkedDate(Contract contract, LocalDate fromDate,LocalDate toDate) {
        if(!(fromDate.isEqual(LocalDate.now()) || fromDate.isAfter(LocalDate.now()))) {
            throw new InvalidValueException("Ngày bắt đầu sử dụng dịch vụ phải lớn hơn hoặc bằng ngày hiện tại");
        }
        if(!toDate.isAfter(fromDate)) {
            throw new InvalidValueException("Ngày kết thúc sử dụng dịch vụ phải lớn hơn ngày bắt đầu");
        }
        // List<Contract> contracts = contractRepository.findAll();
        // for(Contract c : contracts) {
        //     LocalDate checkinDate = c.getCheckinDate();
        //     LocalDate checkoutDate = c.getCheckoutDate();
        //     if((fromDate.isEqual(checkinDate) || fromDate.isAfter(checkinDate)) && (toDate.isEqual(checkoutDate) || toDate.isBefore(checkoutDate)) ) {
        //         return true;
        //     }
        // }
        LocalDate checkinDate = contract.getCheckinDate();
            LocalDate checkoutDate = contract.getCheckoutDate();
            if((fromDate.isEqual(checkinDate) || fromDate.isAfter(checkinDate)) && (toDate.isEqual(checkoutDate) || toDate.isBefore(checkoutDate)) ) {
                return true;
            }
        return false;
    }
    public void deleteById(Integer id) {
        RegisterService registerService = registerServiceRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Không tìm thấy đơn đăng ký dịch vụ này"));
        registerServiceRepository.deleteById(id);
    }
    @Transactional
    public void registerService(RegisterService registerService) {
        User customer = userRepository.findById(registerService.getCustomer().getId())
        .orElseThrow(() -> new NotFoundException("Không tồn tại khách hàng với id: "+registerService.getCustomer().getId()));
        Services service = servicesRepository.findById(registerService.getService().getId())
        .orElseThrow(() -> new NotFoundException("Không tồn tại dịch vụ với id: "+registerService.getService().getId()));
        Contract contract = contractRepository.findById(registerService.getContract().getId())
        .orElseThrow(() -> new NotFoundException("Không tồn tại hợp đồng này"));
        if(!checkedDate(contract,registerService.getFromDate(), registerService.getToDate())) {
            throw new InvalidValueException("Vui lòng chọn ngày đăng ký dịch vụ nằm trong thời gian đăng ký phòng");
        }
        
        long daysBetween = ChronoUnit.DAYS.between(registerService.getFromDate(), registerService.getToDate());
        registerService.setTotalPrice(service.getPrice() * daysBetween);
        registerServiceRepository.save(registerService);
    }
    public List<RegisterServiceResponseDTO> getAllRegister() {
        List<RegisterService> registerServices = registerServiceRepository.findAll();
        List<RegisterServiceResponseDTO> registerServiceResponseDTOs = new ArrayList<>();
        for(RegisterService r: registerServices) {
            RegisterServiceResponseDTO registerServiceResponseDTO = new RegisterServiceResponseDTO();
            registerServiceResponseDTO.setContractId(r.getContract().getId());
            registerServiceResponseDTO.setId(r.getId());
            registerServiceResponseDTO.setFromDate(r.getFromDate());
            registerServiceResponseDTO.setServiceName(r.getService().getName());
            registerServiceResponseDTO.setStatus(r.getStatus());
            registerServiceResponseDTO.setToDate(r.getToDate());
            registerServiceResponseDTO.setTotalPrice(r.getTotalPrice());
            registerServiceResponseDTOs.add(registerServiceResponseDTO);
        }
        return registerServiceResponseDTOs; 
    }
}
