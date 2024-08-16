package com.fisa.study.management.domain.checkup.service;

import com.fisa.study.management.domain.checkup.dto.ReceiveCheckUpDTO;
import com.fisa.study.management.domain.checkup.dto.SendCheckUpDTO;
import com.fisa.study.management.domain.checkup.entity.CheckUp;
import com.fisa.study.management.domain.checkup.repository.CheckUpRepository;
import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckUpService {
    private final CheckUpRepository checkUpRepository;
    private final RoomRepository roomRepository;

    public Long registerCheckUpForRoom(UUID uuid, ReceiveCheckUpDTO receiveCheckUpDTO){
        Optional<Room> _room =roomRepository.findByUuid(uuid);
        if (_room.isEmpty()){
            throw new Error("Room with ID " + uuid + " not found.");
            //에러 처리 고민
        }
        return checkUpRepository.save(DTOToEntityWithRoom(_room.get(),receiveCheckUpDTO)).getId();
    }

    public SendCheckUpDTO getCheckUpResult(Long checkupId){
        CheckUp checkUp= checkUpRepository.findById(checkupId).get();
        return EntityToDTO(checkUp);
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
