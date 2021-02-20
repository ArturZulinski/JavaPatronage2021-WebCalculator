package com.patronage.calculator.repository;

import com.patronage.calculator.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findByTimeOfLogBetween(LocalDateTime fromDate, LocalDateTime toDate);

    List<History> findByTimeOfLogAfter(LocalDateTime fromDate);

}
