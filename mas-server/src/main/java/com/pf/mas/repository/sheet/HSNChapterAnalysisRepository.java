package com.pf.mas.repository.sheet;

import com.pf.mas.model.entity.sheet.HSNChapterAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HSNChapterAnalysisRepository extends JpaRepository<HSNChapterAnalysis, Long> {
}
