package com.fisa.study.management.domain.snapshot.repository;

import com.fisa.study.management.domain.snapshot.entity.Snapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SnapshotRepository extends JpaRepository<Snapshot,Long> {



    Snapshot findTopByRoomIdOrderByIdDesc(Long roomId);




    @Query("select s from Snapshot s where s.room.uuid = :uuid " +
            "and FUNCTION('MONTH', s.createdDate) =  :month " +
            "and FUNCTION('YEAR', s.createdDate) =  :year "+
            "and FUNCTION('DAY', s.createdDate) =  :day ")
    List<Snapshot> findCreatedDateByRoomUuidAndDay(@Param("uuid") UUID uuid, @Param("year") int year ,
                                                 @Param("month") int month, @Param("day") int day);

    @Query("SELECT s.createdDate FROM Snapshot s " +
            "WHERE s.room.uuid = :uuid " +
            "AND FUNCTION('MONTH', s.createdDate) = :month " +
            "AND FUNCTION('YEAR', s.createdDate) = :year")
    List<LocalDateTime> findDistinctCreatedDatesByRoomUuidAndMonth
            (@Param("uuid") UUID uuid, @Param("year") int year, @Param("month") int month);
}
