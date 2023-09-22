package com.homestay.roomtype;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.homestay.image.ImageService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/roomtype")
public class RoomTypeResource {
    
    private RoomTypeService roomTypeService;

    @Autowired
    public RoomTypeResource(RoomTypeService roomTypeService, ImageService imageService) {
        this.roomTypeService = roomTypeService;
    }
    
    @GetMapping()
    public ResponseEntity<List<RoomTypeResponseDTO>> getAllRoomType() {
        List<RoomTypeResponseDTO> roomTypeResponseDTOs = roomTypeService.getAllRoomType();
        return ResponseEntity.status(HttpStatus.OK).body(roomTypeResponseDTOs);
    }
    @GetMapping("{id}")
    public ResponseEntity<RoomTypeResponseDTO> getRoomTypeById(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(roomTypeService.getRoomTypeById(id));
    }
}
