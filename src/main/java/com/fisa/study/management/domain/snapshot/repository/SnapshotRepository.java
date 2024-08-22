package com.fisa.study.management.domain.snapshot.repository;

import com.fisa.study.management.domain.snapshot.entity.Snapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SnapshotRepository extends JpaRepository<Snapshot,Long> {


    List<Snapshot> findByRoomId(Long roomId);

    Snapshot findTopByRoomIdOrderByIdDesc(Long roomId);

//    @Query("select s.createdDate from Snapshot s where s.room.id = :roomId")
//    List<LocalDateTime> findCreatedDateByRoom_Id(@Param("roomId") Long roomId);
//List<Snapshot> findSnapshotsByRoom_IdAndCreatedDateBetween(Long roomId, LocalDateTime startDate, LocalDateTime endDate);
//@Query("select s from Snapshot s where s.room.id = :roomId and s.createdDate between :startDate and :endDate")
//List<Snapshot> findSnapshotsByCreatedDate(Long roomId, LocalDateTime startDate, LocalDateTime endDate);



}
