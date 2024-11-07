package com.calis100.CalisAPI.service.impl;

import com.calis100.CalisAPI.model.Challenge;
import com.calis100.CalisAPI.model.User;
import com.calis100.CalisAPI.model.enums.Status;
import com.calis100.CalisAPI.repository.ChallengeRepository;
import com.calis100.CalisAPI.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ChallengeServiceImpl implements ChallengeService {
    private final ChallengeRepository challengeRepository;

    @Autowired
    public ChallengeServiceImpl(ChallengeRepository challengeRepository) {
        this.challengeRepository = challengeRepository;
    }

    // create new
    @Override
    public Challenge startChallenge(Challenge challenge, User user) {
        if (notStarted(challenge) || isCompleted(challenge) || isFailed(challenge)) {
            challenge = new Challenge();
            challenge.setUser(user);
            challenge.setStatus(Status.Active);
            return challengeRepository.save(challenge);
        }
        return challengeRepository.save(challenge);
    }

    // for dashboard
    @Override
    public Challenge getActiveChallenge(User user) {
        List<Challenge> challengeList = user.getChallenges();

        if (!challengeList.isEmpty()) {
            for (Challenge c: challengeList) {
                if(c.getStatus() == Status.Active) {
                    return challengeRepository.findByChallengeId(c.getChallengeId());
                }
            }
        }
        Challenge challenge = new Challenge();
        challenge.setUser(user);
        challenge.setStatus(Status.Active);
        challenge.setDayCount(1);
        challenge.setCreatedAt(LocalDateTime.now());
        challenge.setLastUpdate(LocalDateTime.now());
        challengeRepository.save(challenge);
        return challengeRepository.findByUser(user);
    }

    @Override
    public Challenge updateChallenge(Challenge challenge) {
        if (isActive(challenge)) {
            challenge.setLastUpdate(LocalDateTime.now());
            LocalDateTime dateStart = challenge.getLastUpdate();
            LocalDateTime dateEnd = challenge.getUpdateAt();
            long last = ChronoUnit.DAYS.between(dateStart, dateEnd);

            if (last > 1) {
                challenge.setStatus(Status.Failed);
            }
            if (last > 100) {
                challenge.setStatus(Status.Completed);
            }
            if (isCompleted(challenge)) {
                return new Challenge();
            }
            challengeRepository.save(challenge);
        }
        updateStatusFlag(challenge);
        return challengeRepository.save(challenge);
    }

    private void updateStatusFlag(Challenge challenge) {
        if (challenge.getStatus() == Status.Active) {
            isActive(challenge);
        } else if (challenge.getStatus() == Status.Completed) {
            isCompleted(challenge);
        } else if (challenge.getStatus() == Status.Failed) {
            isFailed(challenge);
        } else {
            notStarted(challenge);
        }
    }

    public List<Challenge> getAllChallenges(User user) {
        List<Challenge> challengeList = new ArrayList<>(challengeRepository.getChallengeByUser(user));
        return challengeList;
    }
    public boolean isActive(Challenge challenge) {
        return challenge.getStatus() == Status.Active;

    }

    public boolean isCompleted(Challenge challenge) {
        return challenge.getStatus() == Status.Completed;
    }

    public boolean isFailed(Challenge challenge) {
        return challenge.getStatus() == Status.Failed;

    }

    public boolean notStarted(Challenge challenge) {
        return challenge.getStatus() == Status.None;
    }
}
