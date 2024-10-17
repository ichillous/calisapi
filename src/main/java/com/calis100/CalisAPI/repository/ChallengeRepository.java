package com.calis100.CalisAPI.repository;

import com.calis100.CalisAPI.model.Challenge;
import com.calis100.CalisAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByUser(User user);
    Challenge findChallengeByChallengeId(Challenge challenge);
}
