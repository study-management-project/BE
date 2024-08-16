package com.fisa.study.management.domain.snapshot.controller;


import com.fisa.study.management.domain.snapshot.dto.SendSnapshotDTO;
import com.fisa.study.management.domain.snapshot.dto.RegSnapshotDTO;
import com.fisa.study.management.domain.snapshot.service.SnapshotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/room")
public class SnapshotController {
    private final SnapshotService snapshotService;
    @GetMapping("/{roomId}/snapshot")
    public Map<String, Object>  getFirst(@PathVariable Long roomId){
        List<SendSnapshotDTO> sendSnapshotDTOList = snapshotService.getSnapshotFromRoomFirst(roomId);
        List<LocalDate> localDateList= snapshotService.getCreatedDatesByRoomId(roomId);
        Map<String, Object> attributes=new HashMap<>();
        attributes.put("snapshotDTOList", sendSnapshotDTOList);
        attributes.put("localDateList", localDateList);
        return attributes;
    }
    @PostMapping("/{roomId}/snapshot/register")
    public void regSnapshot(@PathVariable Long roomId, @RequestBody RegSnapshotDTO regSnapshotDTO){
        snapshotService.regSnapshot(roomId, regSnapshotDTO);
    }

    @GetMapping("/{roomId}/snapshot/{selectDate}")
    public List<SendSnapshotDTO>  getSnapshotByDate(@PathVariable Long roomId,@PathVariable String selectDate){
        LocalDate localDate = LocalDate.parse(selectDate);

        return snapshotService.getSnapshotFromRoomSelectDate(roomId, localDate);
    }
}

