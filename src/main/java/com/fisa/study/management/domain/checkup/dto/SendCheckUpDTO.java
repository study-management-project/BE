package com.fisa.study.management.domain.checkup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendCheckUpDTO {
    private int O;
    private int X;
}
