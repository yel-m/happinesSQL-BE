package com.hobak.happinessql.domain.record.repository;

import com.hobak.happinessql.domain.record.domain.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
