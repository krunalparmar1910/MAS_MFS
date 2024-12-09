package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.CircularTransactionsValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CircularTransactionsValueRepository extends JpaRepository<CircularTransactionsValue, Long> {
}
