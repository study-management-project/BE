package com.fisa.study.management.domain.checkup.service;

import com.fisa.study.management.domain.checkup.dto.CheckUpDTO;
import com.fisa.study.management.domain.checkup.dto.SendCheckUpDTO;
import com.fisa.study.management.domain.checkup.entity.CheckUp;
import com.fisa.study.management.domain.checkup.repository.CheckUpRepository;
import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.repository.RoomRepository;
import com.fisa.study.management.global.error.CustomException;
import com.fisa.study.management.global.error.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;



@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CheckUpServiceImpl implements CheckUpService {
    private final CheckUpRepository checkUpRepository;
    private final RoomRepository roomRepository;

    public CheckUpDTO getCheckUp(UUID uuid){
        roomRepository.findByUuid(uuid)
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));
        Optional<CheckUp> checkUp= checkUpRepository.findTopByRoomUuidOrderByIdDesc(uuid);

        return CheckUpDTO.from(checkUp,uuid);
    }

    public void registerCheckUpForRoom(CheckUpDTO checkUpDTO) {
        Room room= roomRepository.findByUuid(checkUpDTO.getUuid())
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));
        checkUpRepository.save(checkUpDTO.toEntity(room));
    }

    public SendCheckUpDTO getCheckUpResult(UUID uuid) {
        CheckUp checkUp= checkUpRepository.findTopByRoomUuidOrderByIdDesc(uuid)
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));
        checkUp.setIsOpen(false);
        checkUpRepository.save(checkUp);
        return SendCheckUpDTO.from(checkUp);
    }

    public void resentCheckUpOIncrease(UUID uuid)  {
        CheckUp checkUp= checkUpRepository.findTopByRoomUuidOrderByIdDesc(uuid)
                .orElseThrow(() -> new CustomException(ErrorCode.CHECKUP_NOT_FOUND));
        checkUp.addO();
        checkUpRepository.save(checkUp);
    }

    public void resentCheckUpXIncrease(UUID uuid)  {
        CheckUp checkUp= checkUpRepository.findTopByRoomUuidOrderByIdDesc(uuid)
                .orElseThrow(() -> new CustomException(ErrorCode.CHECKUP_NOT_FOUND));
        checkUp.addX();
        checkUpRepository.save(checkUp);
    }
}
