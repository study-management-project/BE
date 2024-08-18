package com.fisa.study.management.domain.checkup.controller;

import com.fisa.study.management.domain.checkup.dto.ReceiveCheckUpDTO;
import com.fisa.study.management.domain.checkup.dto.SendCheckUpDTO;
import com.fisa.study.management.domain.checkup.service.CheckUpService;
import com.fisa.study.management.global.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/room/{uuid}/checkup")
@Slf4j
@RequiredArgsConstructor
public class CheckUpController {
    private final CheckUpService checkUpService;

    // checkup 등록
    //login
    @PostMapping("/register")
    public Long registerCheckUpForRoom(@Login Long userId, @PathVariable UUID uuid, @RequestBody ReceiveCheckUpDTO receiveCheckUpDTO) throws Exception {
        log.info(receiveCheckUpDTO.toString());
        return checkUpService.registerCheckUpForRoom(userId, uuid, receiveCheckUpDTO);
    }

    // ox 개수 반환
    //login
    @GetMapping("/endTime/{checkupId}")
    public SendCheckUpDTO sendCheckUpResult(@PathVariable String uuid, @PathVariable Long checkupId){
        return checkUpService.getCheckUpResult(checkupId);
        return null;
    }


    @GetMapping("/OK")
    public String OIncrease(@PathVariable UUID uuid)throws IllegalAccessException{
        return checkUpService.resentCheckUpOIncrease(uuid);
        return null;
    }

    @GetMapping("/NO")
    public String XIncrease(@PathVariable UUID uuid)throws IllegalAccessException{
        return checkUpService.resentCheckUpXIncrease(uuid);
    }


}
