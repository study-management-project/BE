package com.fisa.study.management.domain.snapshot.controller;


import com.fisa.study.management.domain.snapshot.dto.SendSnapshotDTO;
import com.fisa.study.management.domain.snapshot.dto.RegSnapshotDTO;
import com.fisa.study.management.domain.snapshot.service.SnapshotService;
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

    @GetMapping("/")
    public List<SendSnapshotDTO> getSnapshotsByRoomId(@PathVariable UUID uuid){
        return snapshotService.getSnapshotAll(uuid);
//        List<SendSnapshotDTO> sendSnapshotDTOList = snapshotService.getSnapshotFromRoomFirst(roomId);
//        List<LocalDate> localDateList= snapshotService.getCreatedDatesByRoomId(roomId);
//        Map<String, Object> attributes=new HashMap<>();
//        attributes.put("snapshotDTOList", sendSnapshotDTOList);
//        attributes.put("localDateList", localDateList);
//        return attributes;
    }
    @PostMapping("/register")
    public void registerSnapshotForRoom(@PathVariable UUID uuid, @RequestBody RegSnapshotDTO regSnapshotDTO){
        snapshotService.regSnapshot(uuid, regSnapshotDTO);
    }
    // 최신한개 보내기
    @GetMapping("/last")
    public SendSnapshotDTO sendLastOne(@PathVariable UUID uuid){
        return snapshotService.getLastOne(uuid);
    }

//    @GetMapping("/{roomId}/snapshot/{year}/month")
//    public List<LocalDate> getSnapshotByMonth(@PathVariable Long roomId,@PathVariable String year,@PathVariable String month){
//        return snapshotService.getCreatedDatesByRoomId(roomId);
//    }
//
//    @GetMapping("/{roomId}/snapshot/{selectDate}")
//    public List<SendSnapshotDTO>  getSnapshotByDate(@PathVariable Long roomId,@PathVariable String selectDate){
//        LocalDate localDate = LocalDate.parse(selectDate);
//
//        return snapshotService.getSnapshotFromRoomSelectDate(roomId, localDate);
//    }
}

