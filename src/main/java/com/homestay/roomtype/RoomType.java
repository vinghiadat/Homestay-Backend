package com.homestay.roomtype;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.homestay.image.Image;
import com.homestay.room.Room;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // Tạo bảng trong CSDL
public class RoomType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Name cannot be empty")
    private String name;
    @NotNull(message = "NumberOfAdult cannot be null")
    private Integer numberOfAdult;
    @NotNull(message = "Number of child cannot be null")
    private Integer numberOfChild;
    @NotEmpty(message = "Bed type cannot be empty")
    private String bedType;
    @NotNull(message = "Price cannot be empty")
    private Float price;
    private Boolean isFull = false;
    private Boolean enable = true;
    private LocalDate createdDate = LocalDate.now();
    private LocalDate updatedDate = LocalDate.now();
    @JsonSerialize
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roomType")//ánh xạ tên biến bên Image
    private List<Image> images;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roomType") // Một RoomType có nhiều Room, ánh xạ đến trường roomType trong lớp Room
    private List<Room> rooms;
    
    

}
