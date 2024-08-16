package com.fisa.study.management.domain.snapshot.service;

import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.repository.RoomRepository;
import com.fisa.study.management.domain.snapshot.dto.SendSnapshotDTO;
import com.fisa.study.management.domain.snapshot.dto.RegSnapshotDTO;
import com.fisa.study.management.domain.snapshot.entity.Snapshot;
import com.fisa.study.management.domain.snapshot.repository.SnapshotRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Transactional
@Service
@RequiredArgsConstructor
public class SnapshotService {
    private final SnapshotRepository snapshotRepository;
    private final RoomRepository roomRepository;

    public List<SendSnapshotDTO> getSnapshotAll(UUID uuid){
        Optional<Room> _room =roomRepository.findByUuid(uuid);
        if (_room.isEmpty()){
            throw new Error("Room with ID " + uuid + " not found.");
        }

        return snapshotRepository.findAllByRoom_Id(_room.get().getId()).stream().map(this::EntityToSendSnapshotDTO).collect(Collectors.toList());
    }

    public void regSnapshot(UUID uuid, RegSnapshotDTO regSnapshotDTO){
        Optional<Room> _room =roomRepository.findByUuid(uuid);
        if (_room.isEmpty()){
            throw new Error("Room with ID " + uuid + " not found.");
            //에러 처리 고민
        }
        snapshotRepository.save(regSnapshotDTOToEntity(_room.get(), regSnapshotDTO));
    }
    public SendSnapshotDTO getLastOne(UUID uuid){
        Optional<Room> _room =roomRepository.findByUuid(uuid);
        if (_room.isEmpty()){
            throw new Error("Room with ID " + uuid + " not found.");
            //에러 처리 고민
        }
        Snapshot snapshot= snapshotRepository.findTopByOrderByRoom_IdDesc(_room.get().getId());
        return SendSnapshotDTO.builder()
                .content(snapshot.getContent())
                .createDate(snapshot.getCreatedDate())
                .build();
    }
    SendSnapshotDTO EntityToSendSnapshotDTO(Snapshot snapshot){
        return SendSnapshotDTO.builder()
                .content(snapshot.getContent())
                .createDate(snapshot.getCreatedDate())
                .build();
    }
    Snapshot regSnapshotDTOToEntity(Room room, RegSnapshotDTO regSnapshotDTO){
        Snapshot snapshot= Snapshot.builder()
                .content(regSnapshotDTO.getContent())
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
