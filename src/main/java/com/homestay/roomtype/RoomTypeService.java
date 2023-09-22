package com.homestay.roomtype;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homestay.exception.NotFoundException;
@Service
public class RoomTypeService {
    
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    
    private RoomTypeResponseDTO convertRoomTypeToResponseDTO(RoomType roomType) {
        RoomTypeResponseDTO roomTypeResponseDTO = new RoomTypeResponseDTO();
        roomTypeResponseDTO.setId(roomType.getId());
        roomTypeResponseDTO.setName(roomType.getName());
        roomTypeResponseDTO.setNumberOfAdult(roomType.getNumberOfAdult());
        roomTypeResponseDTO.setNumberOfChild(roomType.getNumberOfChild());
        roomTypeResponseDTO.setBedType(roomType.getBedType());
        roomTypeResponseDTO.setPrice(roomType.getPrice());
        roomTypeResponseDTO.setIsFull(roomType.getIsFull());
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
