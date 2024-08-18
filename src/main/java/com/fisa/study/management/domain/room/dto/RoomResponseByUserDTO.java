package com.fisa.study.management.domain.room.dto;

import com.fisa.study.management.domain.checkup.dto.ResponseFirstCheckUpDTO;
import com.fisa.study.management.domain.checkup.entity.CheckUp;
import com.fisa.study.management.domain.comment.dto.CommentDTO;
import com.fisa.study.management.domain.comment.entity.Comment;
import com.fisa.study.management.domain.snapshot.dto.SendSnapshotDTO;
import com.fisa.study.management.domain.snapshot.entity.Snapshot;
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
    private List<SendSnapshotDTO> snapshotList;
    private List<CommentDTO> commentList;
    private ResponseFirstCheckUpDTO checkUp;
}
