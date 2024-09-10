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
        Room room= roomRepository.findByUuid(dto.getUuid())
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));
        Snapshot snapshot= RegSnapshotDTO.from(dto);
        snapshot.setRoom(room);
        snapshotRepository.save(snapshot);
        return snapshot;
    }

    public Integer[] getSnapshotDateByDate(UUID uuid, int year,int month) {
        return  snapshotRepository
                .findDistinctCreatedDatesByRoomUuidAndMonth(uuid,year,month)
                .stream().map(LocalDateTime::getDayOfMonth).distinct().toArray(Integer[]::new);
    }

    public List<ResSnapshotDTO> findSnapShotByRoomIdAndDay(UUID uuid,int year,int month,int day)  {
        List<Snapshot> snapshots = snapshotRepository.findCreatedDateByRoomUuidAndDay(uuid,year,month,day);
        return snapshots.stream().map(ResSnapshotDTO::from).collect(Collectors.toList());
    }
}
