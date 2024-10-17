package com.calis100.CalisAPI.service;

import com.calis100.CalisAPI.model.Challenge;
import com.calis100.CalisAPI.model.User;

public interface ChallengeService {
    void saveChallenge(Challenge challenge);
    void updateChallengeProgress(Challenge challenge);
    Challenge getActiveChallenge(User user);
}
