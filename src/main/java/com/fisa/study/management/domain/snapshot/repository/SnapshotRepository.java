package com.fisa.study.management.domain.snapshot.repository;

import com.fisa.study.management.domain.snapshot.entity.Snapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SnapshotRepository extends JpaRepository<Snapshot,Long> {



    Snapshot findTopByRoomIdOrderByIdDesc(Long roomId);




    @Query("select s from Snapshot s where s.room.id = :roomId " +
            "and FUNCTION('MONTH', s.createdDate) =  :month " +
            "and FUNCTION('YEAR', s.createdDate) =  :year "+
            "and FUNCTION('DAY', s.createdDate) =  :day ")
    List<Snapshot> findCreatedDateByRoomIdAndDay(@Param("roomId") Long roomId, @Param("year") int year ,
                                                 @Param("month") int month, @Param("day") int day);

    @Query("SELECT s.createdDate FROM Snapshot s " +
            "WHERE s.room.id = :roomId " +
            "AND FUNCTION('MONTH', s.createdDate) = :month " +
            "AND FUNCTION('YEAR', s.createdDate) = :year")
    List<LocalDateTime> findDistinctCreatedDatesByRoomIdAndMonth
            (@Param("roomId") Long roomId, @Param("year") int year,@Param("month") int month);
}
