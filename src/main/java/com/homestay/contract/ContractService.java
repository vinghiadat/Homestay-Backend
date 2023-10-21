package com.homestay.contract;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homestay.exception.InvalidValueException;
import com.homestay.exception.NotFoundException;
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
    @Autowired
    public ContractService(ContractRepository contractRepository, RoomTypeRepository roomTypeRepository,
            RoomRepository roomRepository, UserRepository userRepository) {
        this.contractRepository = contractRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
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
        int comparison2 = LocalDate.now().compareTo(checkinDate);
        if (!(comparison < 0)) {
            throw new InvalidValueException("Ngày đến phải nhỏ hơn ngày đi");
        }
        if (!(comparison2 < 0)) {
            throw new InvalidValueException("Ngày đến phải lớn hơn ngày hiện tại");
        }
        long daysBetween = ChronoUnit.DAYS.between(checkinDate, checkoutDate);
        Float totalPrice = roomType.getPrice()*daysBetween;
        contract.setTotalPrice(totalPrice);
        // Kiểm tra xem phòng đã tồn tại các hợp đồng khác trong khoảng thời gian này
        List<Contract> existingContracts = contractRepository.findByNumberRoom(contract.getNumberRoom());
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
    
}
 