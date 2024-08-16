package com.fisa.study.management.domain.checkup.repository;

import com.fisa.study.management.domain.checkup.entity.CheckUp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckUpRepository extends JpaRepository<CheckUp,Long> {
}
