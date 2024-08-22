package com.fisa.study.management.domain.snapshot.service;

import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.repository.RoomRepository;
import com.fisa.study.management.domain.room.service.RoomService;
import com.fisa.study.management.domain.snapshot.dto.ResSnapshotDTO;
import com.fisa.study.management.domain.snapshot.dto.RegSnapshotDTO;
import com.fisa.study.management.domain.snapshot.entity.Snapshot;
import com.fisa.study.management.domain.snapshot.repository.SnapshotRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Transactional
@Service
@RequiredArgsConstructor
public class SnapshotService {
    private final SnapshotRepository snapshotRepository;
    private final RoomRepository roomRepository;

    public List<ResSnapshotDTO> getSnapshotAll(UUID uuid)  {
        Room room= roomRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException("Room not found"));
        List<Snapshot> snapshots = snapshotRepository.findCreatedDateByRoomIdAndMonth(room.getId(),LocalDate.now());
        return snapshots.stream().map(this::EntityToSendSnapshotDTO).collect(Collectors.toList());
    }

    public Snapshot regSnapshot(Long userId, RegSnapshotDTO dto) throws IllegalAccessException {
        Room room= roomRepository.findByUuid(dto.getUuid()).orElseThrow(() -> new EntityNotFoundException("Room not found"));
        if (!Objects.equals(room.getMember().getId(), userId)) {
            throw new IllegalAccessException("권한이 없습니다.");
        }
        return snapshotRepository.save(regSnapshotDTOToEntity(room, dto));
    }

    public ResSnapshotDTO entityToSendSnapshotDTO(Snapshot snapshot){
        return ResSnapshotDTO.builder()
                .title(snapshot.getTitle())
                .content(snapshot.getContent())
                .createdDate(snapshot.getCreatedDate())
                .build();
    }

    public Integer[] getSnapshotByDate(UUID uuid, LocalDate date) {
        Room room= roomRepository.findByUuid(uuid).orElseThrow(() -> new EntityNotFoundException("Room not found"));
        List<Snapshot> snapshots = snapshotRepository.findCreatedDateByRoomIdAndMonth(room.getId(),date);
        return snapshots.stream().map(snapshot -> snapshot.getCreatedDate().getDayOfMonth()).toArray(Integer[]::new);
    }
    ResSnapshotDTO EntityToSendSnapshotDTO(Snapshot snapshot){
        return ResSnapshotDTO.builder()
                .content(snapshot.getContent())
                .createdDate(snapshot.getCreatedDate())
                .build();
    }
    Snapshot regSnapshotDTOToEntity(Room room, RegSnapshotDTO regSnapshotDTO){
        Snapshot snapshot= Snapshot.builder()
                .content(regSnapshotDTO.getContent())
                .title(regSnapshotDTO.getTitle())
                .room(room)
                .build();
        snapshot.setRoom(room);
        return snapshot;
    }

//    public List<LocalDate> getCreatedDatesByRoomId(Long roomId) {
//        List<LocalDateTime> localDateTimes = snapshotRepository.findCreatedDateByRoom_Id(roomId);
//        return localDateTimes.stream()
//                .map(LocalDateTime::toLocalDate)
//                .distinct()
//                .collect(Collectors.toList());
//    }
//
//    public List<SendSnapshotDTO> getSnapshotFromRoomSelectDate(Long roomId, LocalDate selectDate){
//        Optional<Room> _room =roomRepository.findById(roomId);
//        if (_room.isEmpty()){
//            throw new Error("Room with ID " + roomId + " not found.");
//        }
//
//        LocalDateTime startOfDay = selectDate.atStartOfDay();
//        LocalDateTime endOfDay = selectDate.plusDays(1).atStartOfDay();
//        List<Snapshot> snapshots =snapshotRepository.findSnapshotsByRoom_IdAndCreatedDateBetween(roomId,startOfDay,endOfDay);
//        return snapshots.stream().map(this::EntityToSendSnapshotDTO).collect(Collectors.toList());
//    }
//public List<SendSnapshotDTO> getSnapshotFromRoomFirst(Long roomId){
//    Optional<Room> _room =roomRepository.findById(roomId);
//    if (_room.isEmpty()){
//        throw new Error("Room with ID " + roomId + " not found.");
//        //에러 처리 고민
//    }
//    LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
//    LocalDateTime endOfDay = startOfDay.plusDays(1);
//    List<Snapshot> snapshots=snapshotRepository.findSnapshotsByRoom_IdAndCreatedDateBetween(roomId,startOfDay,endOfDay);
//    return snapshots.stream().map(this::EntityToSendSnapshotDTO).collect(Collectors.toList());
//}
}
