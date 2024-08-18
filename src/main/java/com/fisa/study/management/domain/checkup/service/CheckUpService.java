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

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CheckUpService {
    private final CheckUpRepository checkUpRepository;
    private final RoomRepository roomRepository;

    public Long registerCheckUpForRoom(UUID uuid, ReceiveCheckUpDTO receiveCheckUpDTO)throws Exception{
        Optional<Room> _room =roomRepository.findByUuid(uuid);
        if (_room.isEmpty()){
            throw  new IllegalAccessException("권한이 없습니다.");
            //에러 처리 고민
        }
        return checkUpRepository.save(DTOToEntityWithRoom(_room.get(),receiveCheckUpDTO)).getId();
    }

    public SendCheckUpDTO getCheckUpResult(Long checkupId){
        Optional<CheckUp> _checkUp =checkUpRepository.findById(checkupId);
        if (_checkUp.isEmpty()){
            return null;
            //에러 처리 고민
        }
        return EntityToDTO(_checkUp.get());
    }
    public String resentCheckUpOIncrease(UUID uuid){
        Optional<Room> _room =roomRepository.findByUuid(uuid);
        if (_room.isEmpty()){
            return "room이 없습니다";
            //에러 처리 고민
        }
        CheckUp checkUp= checkUpRepository.findTopByRoomIdOrderByIdDesc(_room.get().getId());
        checkUp.addO();
        checkUpRepository.save(checkUp);
        return "O증가 성공";
    }
    public String resentCheckUpXIncrease(UUID uuid){
        Optional<Room> _room =roomRepository.findByUuid(uuid);
        if (_room.isEmpty()){
            return "room이 없습니다";
            //에러 처리 고민
        }
        CheckUp checkUp= checkUpRepository.findTopByRoomIdOrderByIdDesc(_room.get().getId());
        checkUp.addX();
        checkUpRepository.save(checkUp);
        return "X증가 성공";
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
