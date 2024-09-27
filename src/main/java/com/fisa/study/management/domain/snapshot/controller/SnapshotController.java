package com.fisa.study.management.domain.snapshot.controller;


import com.fisa.study.management.domain.snapshot.dto.ReqModifySnapshotDTO;
import com.fisa.study.management.domain.snapshot.dto.ResSnapshotDTO;
import com.fisa.study.management.domain.snapshot.service.SnapshotServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSnapshot(@PathVariable UUID uuid, @PathVariable Long id) {
        snapshotService.deleteSnapshot(id);
        return ResponseEntity.ok("스냅샷 삭제 성공");
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<?> modifySnapshot(@PathVariable UUID uuid,@RequestBody ReqModifySnapshotDTO reqModifySnapshotDTO) {
        log.info("여기요"+reqModifySnapshotDTO.toString());
        snapshotService.modifySnapshot(reqModifySnapshotDTO);

        return ResponseEntity.ok("스냅샷 수정 성공");
    }
}

