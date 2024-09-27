package com.fisa.study.management.domain.snapshot.dto;

import com.fisa.study.management.domain.snapshot.entity.Snapshot;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReqModifySnapshotDTO {
    private Long id;
    private String title;
    private String content;
}
