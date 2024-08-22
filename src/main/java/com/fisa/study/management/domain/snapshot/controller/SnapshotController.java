package com.fisa.study.management.domain.snapshot.controller;


import com.fisa.study.management.domain.snapshot.dto.ResSnapshotDTO;
import com.fisa.study.management.domain.snapshot.service.SnapshotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/room/{uuid}/snapshot")
public class SnapshotController {
    private final SnapshotService snapshotService;

    @GetMapping
    public List<ResSnapshotDTO> getSnapshotsByRoomId(@PathVariable UUID uuid) {
        return snapshotService.findCreatedDateByRoomIdAndDay(uuid);
    }

    @GetMapping("/{date}")
    public Integer[] sendSnapshotByDate(@PathVariable UUID uuid,@PathVariable LocalDate date) {
        return snapshotService.getSnapshotDateByDate(uuid,date);
    }
}

