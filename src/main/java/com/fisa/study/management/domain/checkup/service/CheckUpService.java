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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CheckUpService {
    private final CheckUpRepository checkUpRepository;
    private final RoomRepository roomRepository;

    public void registerCheckUpForRoom(CheckUpDTO checkUpDTO) {
        Room room= roomRepository.findByUuid(checkUpDTO.getUuid()).orElseThrow(() -> new EntityNotFoundException("Room not found"));
        checkUpRepository.save(DTOToEntityWithRoom(room, checkUpDTO));
    }

    public SendCheckUpDTO getCheckUpResult(UUID uuid) {
        Room room=roomRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException("Room not found"));
        CheckUp checkUp= checkUpRepository.findTopByRoomUuidOrderByIdDesc(uuid).orElseThrow(() -> new EntityNotFoundException("entity not found"));
        checkUp.setIsOpen(false);
        checkUpRepository.save(checkUp);
        return EntityToDTO(checkUp);
    }
    public ResponseEntity<?> resentCheckUpOIncrease(UUID uuid)  {

        Room room= roomRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException("Room not found"));
        CheckUp checkUp= checkUpRepository.findTopByRoomIdOrderByIdDesc(room.getId()).orElseThrow(() -> new EntityNotFoundException("Checkup not found"));
        checkUp.addO();
        checkUpRepository.save(checkUp);
        return ResponseEntity.ok("O증가 성공");
//        return "O증가 성공";
    }
    public ResponseEntity<?> resentCheckUpXIncrease(UUID uuid)  {
        Room room= roomRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException("Room not found"));
        CheckUp checkUp= checkUpRepository.findTopByRoomIdOrderByIdDesc(room.getId()).orElseThrow(() -> new EntityNotFoundException("Checkup not found"));
        checkUp.addX();
        checkUpRepository.save(checkUp);
        return ResponseEntity.ok("증가 성공");
    }
    CheckUp DTOToEntityWithRoom(Room room, CheckUpDTO checkUpDTO){
        CheckUp checkUp= CheckUp.builder()
                .title(checkUpDTO.getTitle())
                .isOpen(checkUpDTO.getIsOpen())
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
