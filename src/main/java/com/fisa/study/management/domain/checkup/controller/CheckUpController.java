package com.fisa.study.management.domain.checkup.controller;

import com.fisa.study.management.domain.checkup.service.CheckUpServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/room/{uuid}/checkup")
@Slf4j
@RequiredArgsConstructor
public class CheckUpController {
    private final CheckUpServiceImpl checkUpService;

    @GetMapping("/get")
    public ResponseEntity<?> getCheckUp(@PathVariable UUID uuid) {
        return checkUpService.getCheckUp(uuid);
    }

    @GetMapping("/OK")
    public ResponseEntity<?> OIncrease(@PathVariable UUID uuid){
        return checkUpService.resentCheckUpOIncrease(uuid);
    }

    @GetMapping("/NO")
    public ResponseEntity<?> XIncrease(@PathVariable UUID uuid){
        return checkUpService.resentCheckUpXIncrease(uuid);
    }
}
