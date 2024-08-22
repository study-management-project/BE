package com.fisa.study.management.domain.checkup.controller;

import com.fisa.study.management.domain.checkup.dto.ReceiveCheckUpDTO;
import com.fisa.study.management.domain.checkup.dto.SendCheckUpDTO;
import com.fisa.study.management.domain.checkup.service.CheckUpService;
import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.global.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/room/{uuid}/checkup")
@Slf4j
@RequiredArgsConstructor
public class CheckUpController {
    private final CheckUpService checkUpService;

    // checkup 등록
    // login
    @PostMapping("/register")
    public Long registerCheckUpForRoom(@Login Long userId, @PathVariable UUID uuid, @RequestBody ReceiveCheckUpDTO receiveCheckUpDTO) throws Exception {
        return checkUpService.registerCheckUpForRoom(userId, uuid, receiveCheckUpDTO);
    }

    // ox 개수 반환
    //login
    @GetMapping("/endTime/{checkupId}")
    public SendCheckUpDTO sendCheckUpResult(@Login Long userId, @PathVariable UUID uuid, @PathVariable Long checkupId)throws Exception{
        return checkUpService.getCheckUpResult(userId,uuid,checkupId);
    }


    @GetMapping("/OK")
    public String OIncrease(@PathVariable UUID uuid){
        return checkUpService.resentCheckUpOIncrease(uuid);

    }

    @GetMapping("/NO")
    public String XIncrease(@PathVariable UUID uuid){
        return checkUpService.resentCheckUpXIncrease(uuid);
    }


}
//public Long registerCheckUpForRoom(Long userId, UUID uuid, ReceiveCheckUpDTO receiveCheckUpDTO) throws Exception {
//    Room room= roomRepository.findByUuid(uuid).orElseThrow();
//    if (!Objects.equals(room.getMember().getId(), userId)) {
//        throw new IllegalAccessException("권한이 없습니다.");
//    }
//    return checkUpRepository.save(DTOToEntityWithRoom(room,receiveCheckUpDTO)).getId();
//}
