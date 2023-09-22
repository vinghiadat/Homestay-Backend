package com.homestay.roomtype;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.homestay.image.Image;
import com.homestay.room.Room;

import jakarta.persistence.CascadeType;
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
public class RoomTypeResponseDTO {
    private Integer id;
    private String name;
    private Integer numberOfAdult;
    private Integer numberOfChild;
    private String bedType;
    private Float price;
    private Boolean isFull = false;
    private Boolean enable = true;
    private LocalDate createdDate;
    private LocalDate updatedDate;
    private List<Image> images;
}
