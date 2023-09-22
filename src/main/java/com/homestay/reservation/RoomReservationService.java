package com.homestay.reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homestay.exception.AlreadyExistsException;
import com.homestay.exception.InvalidValueException;
import com.homestay.exception.NotFoundException;
import com.homestay.exception.room.RoomNotEnabledException;
import com.homestay.exception.roomreservation.NotSuitableForGender;
import com.homestay.room.Room;
import com.homestay.room.RoomRepository;

import jakarta.transaction.Transactional;

@Service
public class RoomReservationService {
    
    
}
