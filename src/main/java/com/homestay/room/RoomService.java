package com.homestay.room;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homestay.exception.AlreadyExistsException;
import com.homestay.exception.InvalidValueException;
import com.homestay.exception.NotFoundException;
import com.homestay.roomtype.RoomType;
import com.homestay.roomtype.RoomTypeRepository;

@Service
public class RoomService {
    
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    public Room findRoomById(Integer id) {
        if(roomRepository.findById(id).isPresent()) {
            return roomRepository.findById(id).get();
        }
        throw new NotFoundException("Phòng không tồn tại với id + "+id);
    }
    public void createRoom( Room room) {
        RoomType roomType = roomTypeRepository.findById(room.getRoomType().getId())
        .orElseThrow(() -> new NotFoundException("Không tồn tại loại phòng này"));
        if(room.getNumberRoom() <= 0) {
            throw new InvalidValueException("Vui lòng nhập số phòng > 0");
        }
        if(roomRepository.findByNumberRoomAndRoomType_Id(room.getNumberRoom(), roomType.getId()).isPresent()) {
            throw new AlreadyExistsException("Phòng này đã tồn tại rồi");
        }
        roomRepository.save(room);
        
    }
    
    public void updateRoom(Integer id, Room room) {
        Room r = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Không tồn tại phòng với id: " + id));
        r.setEnable(room.getEnable());
        roomRepository.save(r);
    }
    public List<Room> findByRoomType_Id(Integer id) {
        if(roomRepository.findByRoomType_Id(id).isPresent()) {
            return roomRepository.findByRoomType_Id(id).get();
        }else {
            throw new NotFoundException("Không có loại phòng phù hợp cho phòng này với id"+ id);
        }
        
    }
}
