package com.fisa.study.management.domain.room.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CodeDTO {
    private UUID uuid;
    private String content;
}
