package com.fisa.study.management.domain.snapshot.service;

import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.room.repository.RoomRepository;
import com.fisa.study.management.domain.snapshot.dto.ResSnapshotDTO;
import com.fisa.study.management.domain.snapshot.dto.RegSnapshotDTO;
import com.fisa.study.management.domain.snapshot.entity.Snapshot;
import com.fisa.study.management.domain.snapshot.repository.SnapshotRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
@Transactional
@Service
@RequiredArgsConstructor
public class SnapshotServiceImpl implements SnapshotService {
    private final SnapshotRepository snapshotRepository;
    private final RoomRepository roomRepository;

    public Snapshot regSnapshot(RegSnapshotDTO dto) {
        Room room= roomRepository.findByUuid(dto.getUuid()).orElseThrow(() -> new EntityNotFoundException("Room not found"));
        return snapshotRepository.save(regSnapshotDTOToEntity(room, dto));
    }


    public Integer[] getSnapshotDateByDate(UUID uuid, int year,int month) {
        return  snapshotRepository
                .findDistinctCreatedDatesByRoomUuidAndMonth(uuid,year,month)
                .stream().map(LocalDateTime::getDayOfMonth).distinct().toArray(Integer[]::new);
    }

    public List<ResSnapshotDTO> findSnapShotByRoomIdAndDay(UUID uuid,int year,int month,int day)  {
        List<Snapshot> snapshots = snapshotRepository.findCreatedDateByRoomUuidAndDay(uuid,year,month,day);
        return snapshots.stream().map(this::EntityToResSnapShotDTO).collect(Collectors.toList());
    }

    ResSnapshotDTO EntityToResSnapShotDTO(Snapshot snapshot) {
        return ResSnapshotDTO.builder()
                .title(snapshot.getTitle())
                .content(snapshot.getContent())
                .createdDate(snapshot.getCreatedDate())
                .build();
    }

    Snapshot regSnapshotDTOToEntity(Room room, RegSnapshotDTO regSnapshotDTO) {
        Snapshot snapshot= Snapshot.builder()
                .content(regSnapshotDTO.getContent())
                .title(regSnapshotDTO.getTitle())
                .room(room)
                .build();
        snapshot.setRoom(room);
        return snapshot;
    }
    public ResSnapshotDTO entityToSendSnapshotDTO(Snapshot snapshot) {
        return ResSnapshotDTO.builder()
                .title(snapshot.getTitle())
                .content(snapshot.getContent())
                .createdDate(snapshot.getCreatedDate())
                .build();
    }
}
