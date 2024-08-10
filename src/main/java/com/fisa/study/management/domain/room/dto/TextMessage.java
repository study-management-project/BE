package com.fisa.study.management.domain.room.dto;

import lombok.Data;

@Data
public class TextMessage {
    private String roomId;
    private String content;
}
