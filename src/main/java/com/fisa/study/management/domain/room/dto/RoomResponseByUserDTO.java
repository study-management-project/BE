package com.fisa.study.management.domain.room.dto;

import com.fisa.study.management.domain.checkup.dto.CheckUpDTO;
import com.fisa.study.management.domain.snapshot.dto.ResSnapshotDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponseByUserDTO {

    private String name;
    private String description;
    private String content;
}
