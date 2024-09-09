package com.fisa.study.management.domain.snapshot.dto;

import com.fisa.study.management.domain.snapshot.entity.Snapshot;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResSnapshotDTO {
    private String title;
    private String content;
    private LocalDateTime createdDate;

    public static ResSnapshotDTO from(Snapshot snapshot) {
        return ResSnapshotDTO.builder()
                .title(snapshot.getTitle())
                .content(snapshot.getContent())
                .createdDate(snapshot.getCreatedDate())
                .build();
    }
}
