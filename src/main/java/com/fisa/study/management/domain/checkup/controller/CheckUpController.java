package com.fisa.study.management.domain.checkup.controller;

import com.fisa.study.management.domain.checkup.dto.ReceiveCheckUpDTO;
import com.fisa.study.management.domain.checkup.dto.SendCheckUpDTO;
import com.fisa.study.management.domain.checkup.service.CheckUpService;
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
    @PostMapping("/register")
    public Long registerCheckUpForRoom(@PathVariable UUID uuid, @RequestBody ReceiveCheckUpDTO receiveCheckUpDTO) throws Exception{
        log.info(receiveCheckUpDTO.toString());
        return checkUpService.registerCheckUpForRoom(uuid, receiveCheckUpDTO);
    }
    @GetMapping("/endTime/{checkupId}")
    public SendCheckUpDTO sendCheckUpResult( @PathVariable String uuid,@PathVariable Long checkupId){
        return checkUpService.getCheckUpResult(checkupId);
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
