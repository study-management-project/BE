package com.fisa.study.management.domain.room.dto;

import lombok.Data;

@Data
public class TextMessage {
    private Long roomId;
    private String content;
}
