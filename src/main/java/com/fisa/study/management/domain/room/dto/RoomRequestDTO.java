package com.fisa.study.management.domain.room.dto;

import com.fisa.study.management.domain.member.entity.Member;
import com.fisa.study.management.domain.room.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequestDTO {
    private String name;
    private String description;

    public Room toEntity(Member member) {
        return Room.builder()
                .name(name)
                .description(description)
                .content("")
                .uuid(UUID.randomUUID())
                .member(member)
                .build();
    }
}
