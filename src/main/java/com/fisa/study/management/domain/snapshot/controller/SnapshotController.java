package com.fisa.study.management.domain.snapshot.controller;


import com.fisa.study.management.domain.snapshot.dto.SendSnapshotDTO;
import com.fisa.study.management.domain.snapshot.dto.RegSnapshotDTO;
import com.fisa.study.management.domain.snapshot.service.SnapshotService;
import com.fisa.study.management.global.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/room/{uuid}/snapshot")
public class SnapshotController {
    private final SnapshotService snapshotService;

    @GetMapping
    public List<SendSnapshotDTO> getSnapshotsByRoomId(@PathVariable UUID uuid) {
        return snapshotService.getSnapshotAll(uuid);
    }

    // 최신한개 보내기
    @GetMapping("/last")
    public SendSnapshotDTO sendLastOne(@PathVariable UUID uuid)  {
        return snapshotService.getLastOne(uuid);
    }
}

