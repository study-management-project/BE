package com.fisa.study.management.domain.room.dto;

import com.fisa.study.management.domain.room.entity.Room;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequestDTO {
    private String name;
    private String description;

    public Room toEntity() {
        return Room.builder()
                .name(name)
                .description(description)
                .content("")
                .build();
    }
}
