package com.fisa.study.management.domain.checkup.service;

import com.fisa.study.management.domain.checkup.dto.ReceiveCheckUpDTO;
import com.fisa.study.management.domain.checkup.dto.SendCheckUpDTO;
import com.fisa.study.management.domain.checkup.entity.CheckUp;
import com.fisa.study.management.domain.checkup.repository.CheckUpRepository;
import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CheckUpService {
    private final CheckUpRepository checkUpRepository;
    private final RoomRepository roomRepository;

    public Long registerCheckUpForRoom(Long userId, UUID uuid, ReceiveCheckUpDTO receiveCheckUpDTO) throws Exception {
        Room room= roomRepository.findByUuid(uuid).orElseThrow();
        if (!Objects.equals(room.getMember().getId(), userId)) {
            throw new IllegalAccessException("권한이 없습니다.");
        }

        return checkUpRepository.save(DTOToEntityWithRoom(room,receiveCheckUpDTO)).getId();
    }


    public SendCheckUpDTO getCheckUpResult(Long checkupId){
        CheckUp checkUp =checkUpRepository.findById(checkupId).orElseThrow();
        return EntityToDTO(checkUp);
    }
    public String resentCheckUpOIncrease(UUID uuid) throws IllegalAccessException {
        Optional<Room> _room =roomRepository.findByUuid(uuid);
        if (_room.isEmpty()){
            throw  new IllegalAccessException("room이 없습니다.");
            //에러 처리 고민
        }
        CheckUp checkUp= checkUpRepository.findTopByRoomIdOrderByIdDesc(_room.get().getId());
        checkUp.addO();
        checkUpRepository.save(checkUp);
        log.info(String.valueOf(checkUp.getOx().getO()));
    }
    public void resentCheckUpXIncrease(UUID uuid) throws IllegalAccessException {
        Optional<Room> _room =roomRepository.findByUuid(uuid);
        if (_room.isEmpty()){
            throw  new IllegalAccessException("room이 없습니다.");
            //에러 처리 고민
        }
        CheckUp checkUp= checkUpRepository.findTopByRoomIdOrderByIdDesc(_room.get().getId());
        checkUp.addX();
        checkUpRepository.save(checkUp);
        log.info(String.valueOf(checkUp.getOx().getO()));
    }
    CheckUp DTOToEntityWithRoom(Room room, ReceiveCheckUpDTO receiveCheckUpDTO){
        CheckUp checkUp= CheckUp.builder()
                .title(receiveCheckUpDTO.getTitle())
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
