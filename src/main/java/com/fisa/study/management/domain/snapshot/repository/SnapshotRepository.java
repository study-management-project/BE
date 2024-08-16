package com.fisa.study.management.domain.snapshot.repository;

import com.fisa.study.management.domain.snapshot.entity.Snapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SnapshotRepository extends JpaRepository<Snapshot,Long> {
    List<Snapshot> findAllByRoomId(Long roomId);
}
