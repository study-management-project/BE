package com.fisa.study.management.domain.snapshot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegSnapshotDTO {
    private UUID uuid;
    private String title;
    private String content;
    private LocalDateTime createDate;
}
