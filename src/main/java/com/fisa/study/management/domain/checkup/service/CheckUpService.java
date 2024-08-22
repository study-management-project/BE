package com.fisa.study.management.domain.checkup.service;

import com.fisa.study.management.domain.checkup.dto.CheckUpDTO;
import com.fisa.study.management.domain.checkup.dto.SendCheckUpDTO;
import com.fisa.study.management.domain.checkup.entity.CheckUp;
import com.fisa.study.management.domain.checkup.repository.CheckUpRepository;
import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CheckUpService {
    private final CheckUpRepository checkUpRepository;
    private final RoomRepository roomRepository;

    public Long registerCheckUpForRoom(Long userId, UUID uuid, CheckUpDTO checkUpDTO) throws Exception {
        Room room= roomRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException("Room not found"));
        if (!Objects.equals(room.getMember().getId(), userId)) {
            throw new IllegalAccessException("권한이 없습니다.");
        }
        return checkUpRepository.save(DTOToEntityWithRoom(room, checkUpDTO)).getId();
    }

    public SendCheckUpDTO getCheckUpResult(Long userId,UUID uuid,Long checkupId) throws IllegalAccessException {
        Room room=roomRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException("Room not found"));
        if (!Objects.equals(room.getMember().getId(), userId)) {
            throw new IllegalAccessException("권한이 없습니다.");
        }

        CheckUp checkUp= checkUpRepository.findById(checkupId).orElseThrow(() -> new EntityNotFoundException("Room not found"));

        return EntityToDTO(checkUp);
    }
    public String resentCheckUpOIncrease(UUID uuid)  {

        Room room= roomRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException("Room not found"));
        CheckUp checkUp= checkUpRepository.findTopByRoomIdOrderByIdDesc(room.getId()).orElseThrow(() -> new EntityNotFoundException("Checkup not found"));
        checkUp.addO();
        checkUpRepository.save(checkUp);
        return "O증가 성공";
    }
    public String resentCheckUpXIncrease(UUID uuid)  {
        Room room= roomRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException("Room not found"));
        CheckUp checkUp= checkUpRepository.findTopByRoomIdOrderByIdDesc(room.getId()).orElseThrow(() -> new EntityNotFoundException("Checkup not found"));
        checkUp.addX();
        checkUpRepository.save(checkUp);
        return "X증가 성공";
    }
    CheckUp DTOToEntityWithRoom(Room room, CheckUpDTO checkUpDTO){
        CheckUp checkUp= CheckUp.builder()
                .title(checkUpDTO.getTitle())
                .build();
        checkUp.setRoom(room);
        return checkUp;
    }
    SendCheckUpDTO EntityToDTO(CheckUp checkUp){
        return SendCheckUpDTO.builder()
                .O(checkUp.getOx().getO())
                .X(checkUp.getOx().getX())
                .build();
    }
}
