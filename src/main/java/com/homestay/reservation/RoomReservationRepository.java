package com.homestay.reservation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.homestay.room.Room;

@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Integer>{
    
}
