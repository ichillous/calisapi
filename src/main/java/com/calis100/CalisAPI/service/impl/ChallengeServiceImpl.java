package com.calis100.CalisAPI.service.impl;

import com.calis100.CalisAPI.model.Challenge;
import com.calis100.CalisAPI.model.User;
import com.calis100.CalisAPI.model.enums.Status;
import com.calis100.CalisAPI.repository.ChallengeRepository;
import com.calis100.CalisAPI.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChallengeServiceImpl implements ChallengeService {
    private final ChallengeRepository challengeRepository;

    @Autowired
    public ChallengeServiceImpl(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    /*
        TODO:
            Thoughts
                - We need to create and update challenges.
    */

    @Override
    public Challenge getActiveChallenge(User user) {
        return challengeRepository.findByUserAndStatus(user, Status.Active);
    }

    @Override
    public void saveChallenge(Challenge challenge) {
        challengeRepository.save(challenge);
    }

    @Override
    public void updateChallengeProgress(Challenge challenge) {
        challenge.setCurrentDay(challenge.getCurrentDay() + 1);
        if (challenge.getCurrentDay() > 100) {
            challenge.setStatus(Status.Completed);
        }
        challengeRepository.save(challenge);
    }

}

