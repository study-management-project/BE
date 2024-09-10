package com.fisa.study.management.domain.checkup.dto;

import com.fisa.study.management.domain.checkup.entity.CheckUp;
import com.fisa.study.management.domain.room.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckUpDTO {
    private UUID uuid;
    private String title;
    private Boolean isOpen;

    public CheckUp toEntity(Room room) {
        CheckUp checkUp = CheckUp.builder()
                .title(this.title)
                .isOpen(true)
                .build();
        checkUp.setRoom(room);
        return checkUp;
    }

    public static CheckUpDTO from(Optional<CheckUp> checkUp, UUID uuid) {
        return checkUp.map(checkUp1 ->
                        CheckUpDTO.builder()
                                .uuid(uuid)
                                .title(checkUp1.getTitle())
                                .isOpen(checkUp1.getIsOpen())
                                .build())
                .orElse(CheckUpDTO.builder()
                        .uuid(uuid)
                        .title("현재 이해도 조사가 없습니다")
                        .isOpen(false)
                        .build());
    }
}
