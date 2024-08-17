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
    public Long registerCheckUpForRoom(@PathVariable UUID uuid, @RequestBody ReceiveCheckUpDTO receiveCheckUpDTO){
        log.info(receiveCheckUpDTO.toString());
        return checkUpService.registerCheckUpForRoom(uuid, receiveCheckUpDTO);
    }

    @GetMapping("/endTime/{checkupId}")
    public SendCheckUpDTO sendCheckUpResult( @PathVariable String uuid,@PathVariable Long checkupId){
        return checkUpService.getCheckUpResult(checkupId);
    }
    @GetMapping("/OK")
    public void OIncrease(@PathVariable UUID uuid){
        checkUpService.resentCheckUpOIncrease(uuid);
    }
    @GetMapping("/NO")
    public void XIncrease(@PathVariable UUID uuid){
        checkUpService.resentCheckUpXIncrease(uuid);
    }


}
