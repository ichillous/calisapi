package com.calis100.CalisAPI.repository;


import com.calis100.CalisAPI.model.Challenge;
import com.calis100.CalisAPI.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findByChallenge(Challenge challenge);
}