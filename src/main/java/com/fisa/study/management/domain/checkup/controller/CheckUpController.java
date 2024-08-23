package com.fisa.study.management.domain.checkup.controller;

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



    @GetMapping("/OK")
    public String OIncrease(@PathVariable UUID uuid){
        return checkUpService.resentCheckUpOIncrease(uuid);
    }

    @GetMapping("/NO")
    public String XIncrease(@PathVariable UUID uuid){
        return checkUpService.resentCheckUpXIncrease(uuid);
    }
}
