package com.fisa.study.management.domain.snapshot.repository;

import com.fisa.study.management.domain.room.entity.Room;
import com.fisa.study.management.domain.snapshot.dto.SendSnapshotDTO;
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
            "and FUNCTION('MONTH', s.createdDate) = FUNCTION('MONTH', :localDate)"+
            "and FUNCTION('YEAR', s.createdDate) = FUNCTION('YEAR', :localDate) ")
    List<Snapshot> findCreatedDateByRoomIdAndMonth(@Param("roomId") Long roomId, @Param("localDate") LocalDate localDate);

//List<Snapshot> findSnapshotsByRoom_IdAndCreatedDateBetween(Long roomId, LocalDateTime startDate, LocalDateTime endDate);
//@Query("select s from Snapshot s where s.room.id = :roomId and s.createdDate between :startDate and :endDate")
//List<Snapshot> findSnapshotsByCreatedDate(Long roomId, LocalDateTime startDate, LocalDateTime endDate);

//날짜를 integer 배열로

}
