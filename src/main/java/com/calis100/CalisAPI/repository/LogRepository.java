package com.calis100.CalisAPI.repository;



import com.calis100.CalisAPI.model.Challenge;
import com.calis100.CalisAPI.model.Log;
import com.calis100.CalisAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;


@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    Optional<Log> findByChallengeAndDate(Challenge challenge, LocalDate date);
    Optional<Log> findTopByChallengeOrderByLogDayDesc(Challenge challenge);
}