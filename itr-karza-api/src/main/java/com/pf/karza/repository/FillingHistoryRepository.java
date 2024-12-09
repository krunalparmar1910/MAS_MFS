package com.pf.karza.repository;

import com.pf.karza.model.entity.FillingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FillingHistoryRepository extends JpaRepository<FillingHistory, Long> {
}
