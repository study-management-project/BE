package com.fisa.study.management.domain.snapshot.dto;

import com.fisa.study.management.domain.snapshot.entity.Snapshot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegSnapshotDTO {
    private UUID uuid;
    private String title;
    private String content;

    public static Snapshot from(RegSnapshotDTO regSnapshotDTO) {
        return Snapshot.builder()
                .content(regSnapshotDTO.getContent())
                .title(regSnapshotDTO.getTitle())
                .build();

    }
}
