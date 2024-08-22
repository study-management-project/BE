package com.fisa.study.management.domain.snapshot.dto;

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
}
