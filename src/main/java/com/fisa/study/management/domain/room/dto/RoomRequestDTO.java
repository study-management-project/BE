package com.fisa.study.management.domain.room.dto;

import com.fisa.study.management.domain.room.entity.Room;
import lombok.*;

import java.util.UUID;

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
                .uuid(UUID.randomUUID())
                .build();
    }
}
