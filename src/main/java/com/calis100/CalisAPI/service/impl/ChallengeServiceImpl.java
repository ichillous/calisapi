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
    public Challenge saveChallenge(Challenge challenge) {
        return challengeRepository.save(challenge);
    }

    @Override
    public Challenge getChallenge(Challenge challenge) {
        // can return entire challenge or challenge values
        return challengeRepository.findChallengeByChallengeId(challenge);
    }

    @Override
    public List<Challenge> getAllChallenges(User user) {
        return challengeRepository.findByUser(user);
    }

    @Override
    public void updateChallengeStatus(Challenge challenge, Status status) {
        challenge.setStatus(status);
        challengeRepository.save(challenge);
    }

}

