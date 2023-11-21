package com.homestay.roomtype;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.homestay.image.ImageService;
import com.homestay.message.SuccessMessage;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4401"})
@RequestMapping("api/v1/roomtype")
public class RoomTypeResource {
    
    private RoomTypeService roomTypeService;

    @Autowired
    public RoomTypeResource(RoomTypeService roomTypeService, ImageService imageService) {
        this.roomTypeService = roomTypeService;
    }
    @PostMapping("add")
    public ResponseEntity<?> addRoomTypeWithImages(@RequestParam("images") List<MultipartFile> imageFiles,
                                                 @RequestParam("name") String name,
                                                 @RequestParam("numberOfAdult") Integer numberOfAdult,
                                                 @RequestParam("numberOfChild") Integer numberOfChild,
                                                 @RequestParam("bedType") String bedType,
                                                 @RequestParam("price") Float price) throws IOException {

        // Call the service method to add RoomType with images
        RoomType savedRoomType = roomTypeService.addRoomTypeWithImages(imageFiles, name,numberOfAdult,numberOfChild,bedType,price);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SuccessMessage("Phòng đã được thêm vào thành công", HttpStatus.CREATED.value()));
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
    @PatchMapping("{id}")
    public ResponseEntity<Void> updateRoomType(@PathVariable Integer id, @RequestBody RoomType roomType) {
        roomTypeService.updateRoomType(id,roomType);
        return ResponseEntity.noContent().build();
    }
}
