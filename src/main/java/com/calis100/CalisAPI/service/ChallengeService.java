package com.calis100.CalisAPI.service;

import com.calis100.CalisAPI.model.Challenge;
import com.calis100.CalisAPI.model.User;
import com.calis100.CalisAPI.model.enums.Status;

import java.util.List;

public interface ChallengeService {
    Challenge saveChallenge(Challenge challenge);
    Challenge getChallenge(Challenge challenge);
    List<Challenge> getAllChallenges(User user);
    void updateChallengeStatus(Challenge challenge, Status status);
}
