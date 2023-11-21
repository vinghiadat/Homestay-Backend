package com.homestay.roomtype;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.homestay.exception.AlreadyExistsException;
import com.homestay.exception.InvalidValueException;
import com.homestay.exception.NotFoundException;
import com.homestay.image.ImageService;

import jakarta.transaction.Transactional;
@Service
public class RoomTypeService {
    
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private ImageService imageService;
    @Transactional
    public void updateRoomType(Integer id, RoomType roomType) {
        RoomType r = roomTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy loại phòng này"));
        r.setEnable(roomType.getEnable());
        roomTypeRepository.save(r);
    }
    public RoomType addRoomTypeWithImages(List<MultipartFile> imageFiles,String name,
                                                 Integer numberOfAdult,
                                                  Integer numberOfChild,
                                                  String bedType,
                                                 Float price ) throws IOException {
        // Check if the room type with the given name already exists
        if (roomTypeRepository.findByName(name).isPresent()) {
            throw new AlreadyExistsException("Loại phòng " + name + " đã tồn tại");
        }
        if(numberOfAdult <=0 || numberOfAdult >10) {
            throw new InvalidValueException("Vui lòng nhập số lượng người lớn lớn hơn 0 và bé hơn = 10");
        }
        if(numberOfChild <=0 || numberOfChild >10) {
            throw new InvalidValueException("Vui lòng nhập số lượng trẻ em lớn hơn 0 và bé hơn = 10");
        }
        // Create RoomType object
        RoomType r = RoomType.builder()
                .name(name)
                .numberOfAdult(numberOfAdult)
                .numberOfChild(numberOfChild)
                .bedType(bedType)
                .price(price)
                .enable(true)
                .createdDate(LocalDate.now())
                .updatedDate(null)
                .build();

        // Save RoomType to get its ID
        RoomType savedRoomType = roomTypeRepository.save(r);

        // Upload images for the RoomType
        for (MultipartFile imageFile : imageFiles) {
            imageService.uploadImage(imageFile, savedRoomType.getId());
        }
        // You can also set other details related to the images if needed

        // Save the updated RoomType
        roomTypeRepository.save(savedRoomType);

        return savedRoomType;
    }
    private RoomTypeResponseDTO convertRoomTypeToResponseDTO(RoomType roomType) {
        RoomTypeResponseDTO roomTypeResponseDTO = new RoomTypeResponseDTO();
        roomTypeResponseDTO.setId(roomType.getId());
        roomTypeResponseDTO.setName(roomType.getName());
        roomTypeResponseDTO.setNumberOfAdult(roomType.getNumberOfAdult());
        roomTypeResponseDTO.setNumberOfChild(roomType.getNumberOfChild());
        roomTypeResponseDTO.setBedType(roomType.getBedType());
        roomTypeResponseDTO.setPrice(roomType.getPrice());
        roomTypeResponseDTO.setEnable(roomType.getEnable());
        roomTypeResponseDTO.setCreatedDate(roomType.getCreatedDate());
        roomTypeResponseDTO.setUpdatedDate(roomType.getUpdatedDate());
        roomTypeResponseDTO.setImages(roomType.getImages());
        return roomTypeResponseDTO;
    }

    private List<RoomTypeResponseDTO> convertListRoomTypeToListResponseDTO(List<RoomType> roomTypes) {
        List<RoomTypeResponseDTO> responses = new ArrayList<>();
        for(RoomType roomType: roomTypes) {
            responses.add(convertRoomTypeToResponseDTO(roomType));
        }
        
        return responses;
    }
    
    public List<RoomTypeResponseDTO> getAllRoomType() {
        List<RoomType> roomTypes = roomTypeRepository.findAll();
        return convertListRoomTypeToListResponseDTO(roomTypes);
    }   

    public RoomTypeResponseDTO getRoomTypeById(Integer id) {
        Optional<RoomType> roomType = roomTypeRepository.findById(id);
        RoomTypeResponseDTO roomTypeResponseDTO = new RoomTypeResponseDTO();
        if(roomType.isPresent()) {
            roomTypeResponseDTO = convertRoomTypeToResponseDTO(roomType.get());
            return roomTypeResponseDTO;
        }else {
            throw new NotFoundException("Loại phòng không tồn tại với id: "+id);
        }
        
    }

}
