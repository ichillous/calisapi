package com.calis100.CalisAPI.repository;

import com.calis100.CalisAPI.model.Challenge;
import com.calis100.CalisAPI.model.User;
import com.calis100.CalisAPI.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
   Challenge findByUser(User user);
   Challenge findByChallengeId(Long challengeId);

    List<Challenge> getChallengeByUser(User user);
}
