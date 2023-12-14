package com.dattp.testbackenddev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.dattp.testbackenddev.entity.History;

public interface HistoryRepository extends JpaRepository<History,Long>{ 
    @Modifying
    @Query(value = "TRUNCATE TABLE HISTORY", nativeQuery = true)
    public void truncate();
}