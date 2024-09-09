package com.fisa.study.management.domain.checkup.dto;

import com.fisa.study.management.domain.checkup.entity.CheckUp;
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

    public static SendCheckUpDTO from(CheckUp checkUp){
        return SendCheckUpDTO.builder()
                .O(checkUp.getOx().getO())
                .X(checkUp.getOx().getX())
                .build();
    }
}
