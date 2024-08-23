package com.fisa.study.management.domain.checkup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckUpDTO {
    private UUID uuid;
    private String title;
    private Boolean isOpen;
}
