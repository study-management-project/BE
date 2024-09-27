package com.fisa.study.management.domain.checkup.controller;

import com.fisa.study.management.domain.checkup.dto.CheckUpDTO;
import com.fisa.study.management.domain.checkup.service.CheckUpServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/room/{uuid}/checkup")
@Slf4j
@RequiredArgsConstructor
public class CheckUpController {
    private final CheckUpServiceImpl checkUpService;

    @GetMapping("/get")
    public ResponseEntity<?> getCheckUp(@PathVariable UUID uuid) {
        CheckUpDTO checkUpDTO = checkUpService.getCheckUp(uuid);
        return ResponseEntity.ok(checkUpDTO);
    }

    @GetMapping("/OK")
    public ResponseEntity<?> OIncrease(@PathVariable UUID uuid) {
        checkUpService.resentCheckUpOIncrease(uuid);
        return ResponseEntity.ok("O증가 성공");
    }

    @GetMapping("/NO")
    public ResponseEntity<?> XIncrease(@PathVariable UUID uuid) {
        checkUpService.resentCheckUpXIncrease(uuid);
        return ResponseEntity.ok("X 증가 성공");
    }
}
