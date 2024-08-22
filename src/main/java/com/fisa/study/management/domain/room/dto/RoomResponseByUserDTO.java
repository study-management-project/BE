package com.fisa.study.management.domain.room.dto;

import com.fisa.study.management.domain.checkup.dto.ReceiveCheckUpDTO;
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
    private List<ResSnapshotDTO> snapshotList;
    private List<String> commentList;
    private ReceiveCheckUpDTO checkUp;
    private Integer[] haveSnapshotDate;
}
