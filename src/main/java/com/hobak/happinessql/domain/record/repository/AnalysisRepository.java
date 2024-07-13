package com.hobak.happinessql.domain.record.repository;

import com.hobak.happinessql.domain.record.domain.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
}
