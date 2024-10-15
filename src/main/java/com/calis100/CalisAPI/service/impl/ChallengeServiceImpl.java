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

    @Override
    public Challenge saveChallenge(Challenge challenge) {
        return challengeRepository.save(challenge);
    }

    @Override
    public Challenge getActiveChallenge(User user) {
        return challengeRepository.findByUserAndStatus(user, Status.ACTIVE)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Challenge> getChallengesByUser(User user) {
        return challengeRepository.findByUser(user);
    }

    @Override
    public void updateChallengeStatus(Challenge challenge, Status status) {
        challenge.setStatus(status);
        challengeRepository.save(challenge);
    }
}

