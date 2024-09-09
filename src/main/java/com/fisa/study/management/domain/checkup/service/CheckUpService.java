package com.fisa.study.management.domain.checkup.service;

import com.fisa.study.management.domain.checkup.dto.CheckUpDTO;
import com.fisa.study.management.domain.checkup.dto.SendCheckUpDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface CheckUpService {
    CheckUpDTO getCheckUp(UUID uuid);

    void registerCheckUpForRoom(CheckUpDTO checkUpDTO) ;

    SendCheckUpDTO getCheckUpResult(UUID uuid);

    String resentCheckUpOIncrease(UUID uuid);

    String resentCheckUpXIncrease(UUID uuid);
}
