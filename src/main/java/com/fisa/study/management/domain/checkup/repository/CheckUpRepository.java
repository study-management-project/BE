package com.fisa.study.management.domain.checkup.repository;

import com.fisa.study.management.domain.checkup.entity.CheckUp;
import com.fisa.study.management.domain.snapshot.entity.Snapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CheckUpRepository extends JpaRepository<CheckUp,Long> {

    @Query("select c from CheckUp c where c.room.id= :roomId order by c.id desc limit 1")
    CheckUp findTopByRoomIdOrderByRoomIdDesc(Long roomId);

}
