package com.homestay.roomtype;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.homestay.contract.Contract;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {
    //Viết hàm tìm kiếm roomtype theo name
    Optional<RoomType> findByName(String name);
}

