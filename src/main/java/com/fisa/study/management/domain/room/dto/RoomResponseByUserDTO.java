package com.fisa.study.management.domain.room.dto;

import com.fisa.study.management.domain.room.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponseByUserDTO {

    private String name;
    private String description;
    private String content;

    public static RoomResponseByUserDTO from(Room room) {
        return RoomResponseByUserDTO.builder()
                .name(room.getName())
                .description(room.getDescription())
                .content(room.getContent())
                .build();
    }
}
