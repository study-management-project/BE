package com.fisa.study.management.domain.snapshot.service;

import com.fisa.study.management.domain.snapshot.dto.RegSnapshotDTO;
import com.fisa.study.management.domain.snapshot.dto.ResSnapshotDTO;
import com.fisa.study.management.domain.snapshot.entity.Snapshot;

import java.util.List;
import java.util.UUID;

public interface SnapshotService {
    Snapshot regSnapshot(RegSnapshotDTO dto);

    Integer[] getSnapshotDateByDate(UUID uuid, int year, int month);
    List<ResSnapshotDTO> findSnapShotByRoomIdAndDay(UUID uuid, int year, int month, int day);


}
