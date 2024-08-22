package com.fisa.study.management.domain.room.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponseByAdminDTO {
    private Long id;
    private UUID uuid;
    private String name;
    private String description;
}
