package com.fisa.study.management.domain.snapshot.service;

import com.fisa.study.management.domain.comment.dto.CommentDTO;
import com.fisa.study.management.domain.comment.entity.Comment;
import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.repository.RoomRepository;
import com.fisa.study.management.domain.snapshot.dto.SnapshotDTO;
import com.fisa.study.management.domain.snapshot.entity.Snapshot;
import com.fisa.study.management.domain.snapshot.repository.SnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SnapshotService {
    private final SnapshotRepository snapshotRepository;
    private final RoomRepository roomRepository;


    public List<SnapshotDTO> getSnapshotFromRoomSelectDate(Long roomId, LocalDate selectDate){
        if (!roomRepository.findById(roomId).isPresent()){
            throw new Error("Room with ID " + roomId + " not found.");
        }

        LocalDateTime startOfDay = selectDate.atStartOfDay();
        LocalDateTime endOfDay = selectDate.plusDays(1).atStartOfDay();
        List<Snapshot> snapshots =snapshotRepository.findSnapshotsByRoom_IdAndCreatedDateBetween(roomId,startOfDay,endOfDay);
        return snapshots.stream().map(this::EntityToDTO).collect(Collectors.toList());
    }
    public List<SnapshotDTO> getSnapshotFromRoomFirst(Long roomId){
        if (!roomRepository.findById(roomId).isPresent()){
            throw new Error("Room with ID " + roomId + " not found.");
            //에러 처리 고민
        }
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        List<Snapshot> snapshots=snapshotRepository.findSnapshotsByRoom_IdAndCreatedDateBetween(roomId,startOfDay,endOfDay);
        return snapshots.stream().map(this::EntityToDTO).collect(Collectors.toList());
    }
    public List<LocalDate> getCreatedDatesByRoomId(Long roomId) {
        List<LocalDateTime> localDateTimes = snapshotRepository.findCreatedDateByRoom_Id(roomId);
        return localDateTimes.stream()
                .map(LocalDateTime::toLocalDate)
                .distinct()
                .collect(Collectors.toList());
    }
    SnapshotDTO EntityToDTO(Snapshot snapshot){
        return SnapshotDTO.builder()
                .content(snapshot.getContent())
                .createDate(snapshot.getCreatedDate())
                .build();
    }
}
