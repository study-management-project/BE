package com.fisa.study.management.domain.snapshot.dto;

import com.fisa.study.management.domain.room.entity.Room;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SnapshotDTO {
    private String content;
}
