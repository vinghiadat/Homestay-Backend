package com.homestay.contract;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homestay.exception.InvalidValueException;
import com.homestay.exception.NotFoundException;
import com.homestay.registerservice.RegisterService;
import com.homestay.registerservice.RegisterServiceRepository;
import com.homestay.room.Room;
import com.homestay.room.RoomRepository;
import com.homestay.roomtype.RoomType;
import com.homestay.roomtype.RoomTypeRepository;
import com.homestay.user.User;
import com.homestay.user.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ContractService {
    private ContractRepository contractRepository;
    private RoomTypeRepository roomTypeRepository;
    private RoomRepository roomRepository;
    private UserRepository userRepository;
    private RegisterServiceRepository registerServiceRepository;
    @Autowired
    public ContractService(ContractRepository contractRepository, RoomTypeRepository roomTypeRepository,
            RoomRepository roomRepository, UserRepository userRepository,RegisterServiceRepository registerServiceRepository) {
        this.contractRepository = contractRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.registerServiceRepository = registerServiceRepository;
    }
    @Transactional
    public void addContract(Contract contract) {
        RoomType roomType = roomTypeRepository.findByName(contract.getRoomType())
        .orElseThrow(() -> new NotFoundException("Không tồn tại loại phòng "+contract.getRoomType()));
        Room room = roomRepository.findByNumberRoomAndRoomType_Id(contract.getNumberRoom(),roomType.getId())
        .orElseThrow(() -> new NotFoundException("Không tồn tại phòng này"));
        User user = userRepository.findById(contract.getCustomer().getId())
        .orElseThrow(() -> new NotFoundException("Không tồn tại khách hàng này"));
        LocalDate checkinDate = contract.getCheckinDate();
        LocalDate checkoutDate = contract.getCheckoutDate();
        int comparison = checkinDate.compareTo(checkoutDate);
        //comparision <0 => ngày đến nhỏ hơn ngày đi
        //comparision==0=> 2 ngày = nhau
        //comparision>0 -> ngày đến lớn hơn ngày đi
        int comparison2 = LocalDate.now().compareTo(checkinDate);
        if (!(comparison < 0)) {
            throw new InvalidValueException("Ngày đến phải nhỏ hơn ngày đi");
        }
        if (!(comparison2 < 0)) {
            throw new InvalidValueException("Ngày đến phải lớn hơn ngày hiện tại");
        }
        long daysBetween = ChronoUnit.DAYS.between(checkinDate, checkoutDate);
        // for (int i = 0; i <= daysBetween; i++) {
        //     LocalDate currentDate = checkinDate.plusDays(i);
        //     System.out.println(currentDate);
        // }
        Float totalPrice = roomType.getPrice()*daysBetween;
        contract.setTotalPrice(totalPrice);
        // Kiểm tra xem phòng đã tồn tại các hợp đồng khác trong khoảng thời gian này
        List<Contract> existingContracts = contractRepository.findByNumberRoomAndRoomType(contract.getNumberRoom(),contract.getRoomType());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        // Kiểm tra xem có hợp đồng nào trùng lặp không
        for (Contract existingContract : existingContracts) {
            if (contract.getCheckinDate().isBefore(existingContract.getCheckoutDate()) &&
                    contract.getCheckoutDate().isAfter(existingContract.getCheckinDate())) {
                throw new 
                InvalidValueException("Phòng đã có hợp đồng trong khoảng thời gian từ ngày: "
                +existingContract.getCheckinDate().format(formatter).toString() +" đến ngày "+ existingContract.getCheckoutDate().format(formatter).toString());
            }
        }
        
        contractRepository.save(contract);
    }
    
    @Transactional
    public void updateContract(Integer contractId, ContractUpdateDTO contractUpdateDTO) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new NotFoundException("Không tồn tại hợp đồng này"));
        RoomType roomType = roomTypeRepository.findByName(contract.getRoomType())
                .orElseThrow(() -> new NotFoundException("Không tồn tại loại phòng nay"));
        Room room = roomRepository.findByNumberRoomAndRoomType_Id(contract.getNumberRoom(), roomType.getId())
                .orElseThrow(() -> new NotFoundException("Không tồn tại phòng này"));
        if (contractUpdateDTO.getStatus() == 2) {
            List<RegisterService> registerServices = registerServiceRepository.findByContractId(contractId);
            for (RegisterService r : registerServices) {
                registerServiceRepository.delete(r);
            }
        }
        contractRepository.updateContractStatusAndNoteAndAdmin(contractId, contractUpdateDTO.getStatus(),
                contractUpdateDTO.getNote(), contractUpdateDTO.getAdmin());
    }

    public List<Contract> getAllContract() {
        return contractRepository.findAll();
    }

    public List<Contract> getContractByUser_Id(Integer id) {
        return contractRepository.findByCustomer_Id(id)
                .orElseThrow(() -> new NotFoundException("Bạn không có lịch sử đặt phòng nào cả"));
    }

    // xóa hợp đồng
    @Transactional
    public void deleteContract(Integer id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tồn tại hợp đồng với id: " + id));
        if (contract.getStatus() != 0) {
            throw new InvalidValueException("Bạn chỉ được xóa hợp đồng khi đang chờ xác nhận");
        }
        List<RegisterService> registerService = registerServiceRepository.findByContractId(contract.getId());
        for (RegisterService r : registerService) {
            registerServiceRepository.delete(r);
        }
        contractRepository.deleteById(id);
    }
}
 