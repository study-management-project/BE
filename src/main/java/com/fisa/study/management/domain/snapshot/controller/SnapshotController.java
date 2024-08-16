package com.fisa.study.management.domain.snapshot.controller;


import com.fisa.study.management.domain.snapshot.dto.SnapshotDTO;
import com.fisa.study.management.domain.snapshot.entity.Snapshot;
import com.fisa.study.management.domain.snapshot.service.SnapshotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/room")
public class SnapshotController {
    private final SnapshotService snapshotService;
    @GetMapping("/{roomId}")
    public void getFirst(@PathVariable Long roomId, Model model){
        List<SnapshotDTO> snapshotDTOList= snapshotService.getSnapshotFromRoomFirst(roomId);
        List<LocalDate> localDateList= snapshotService.getCreatedDatesByRoomId(roomId);
        Map<String, Object> attributes=new HashMap<>();
        attributes.put("snapshotDTOList", snapshotDTOList);
        attributes.put("localDateList", localDateList);
        model.addAllAttributes(attributes);
    }
    @PostMapping("/{roomId}/{selectDate}")
    public void regSnapshot(@PathVariable Long roomId,@PathVariable LocalDate selectDate, Model model){
        List<SnapshotDTO> snapshotDTOList= snapshotService.getSnapshotFromRoomSelectDate(roomId, selectDate);
        model.addAttribute("snapshotDTOList", snapshotDTOList);
    }
}

