package com.fisa.study.management.domain.snapshot.controller;


import com.fisa.study.management.domain.snapshot.dto.ResSnapshotDTO;
import com.fisa.study.management.domain.snapshot.service.SnapshotServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/room/{uuid}/snapshot")
public class SnapshotController {
    private final SnapshotServiceImpl snapshotService;

    @GetMapping("/{year}/{month}")
    public Integer[] sendSnapshotByDate(@PathVariable UUID uuid, @PathVariable int year, @PathVariable int month) {
        return snapshotService.getSnapshotDateByDate(uuid, year, month);
    }

    @GetMapping("/{year}/{month}/{day}")
    public List<ResSnapshotDTO> sendSnapshotByDay(@PathVariable UUID uuid, @PathVariable int year,
                                                  @PathVariable int month, @PathVariable int day) {
        return snapshotService.findSnapShotByRoomIdAndDay(uuid, year, month, day);
    }
}

