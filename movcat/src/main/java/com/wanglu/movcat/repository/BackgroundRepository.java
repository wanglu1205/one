package com.wanglu.movcat.repository;

import com.wanglu.movcat.model.Background;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BackgroundRepository extends JpaRepository<Background, Integer>{

    @Query(value = "SELECT * FROM background ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Background randomfindBackground();

}
