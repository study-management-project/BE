package com.fisa.study.management.domain.snapshot.service;

import com.fisa.study.management.domain.snapshot.dto.SnapshotDTO;
import com.fisa.study.management.domain.snapshot.entity.Snapshot;
import com.fisa.study.management.domain.snapshot.repository.SnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SnapshotService {
    private final SnapshotRepository snapshotRepository;

    public ResponseEntity<Snapshot> getAllSnapshotFromRoom(Long roomId,SnapshotDTO snapshotDTO){
//        return snapshotRepository.
    }
}
