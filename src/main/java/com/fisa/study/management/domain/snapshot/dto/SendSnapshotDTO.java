package com.fisa.study.management.domain.snapshot.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SendSnapshotDTO {
    private String content;
    private LocalDateTime createDate;
}
