package com.calis100.CalisAPI.service.impl;

import com.calis100.CalisAPI.model.Challenge;
import com.calis100.CalisAPI.model.Log;
import com.calis100.CalisAPI.model.User;
import com.calis100.CalisAPI.repository.ChallengeRepository;
import com.calis100.CalisAPI.repository.LogRepository;
import com.calis100.CalisAPI.repository.UserRepository;
import com.calis100.CalisAPI.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LogServiceImpl implements LogService {
    private final LogRepository logRepository;
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;
    @Autowired
    public LogServiceImpl(LogRepository logRepository, UserRepository userRepository, ChallengeRepository challengeRepository) {
        this.logRepository = logRepository;
        this.userRepository = userRepository;
        this.challengeRepository = challengeRepository;
    }

    @Override
    public void saveLog(Log log) {
        logRepository.save(log);
    }

    @Override
    public Log getLogByUserId(Long userId) {
        User user = userRepository.findById(userId).get();
        return logRepository.findById(userId).orElseGet(() -> {
            Log newLog = new Log();
            newLog.setChallenge(new Challenge());
            return logRepository.save(newLog);
        });
    }
    @Override
    public List<Log> getLogsByUser(User user) {
        return logRepository.findAll();
    }
    private int determineNewLogDay(Challenge challenge) {
        Optional<Log> lastLog = logRepository.findTopByChallengeOrderByLogDayDesc(challenge);
        return lastLog.map(log -> log.getLogDay() + 1).orElse(1);
    }

    /* LOGS FOR CURRENT DAY (GET or POST) */
    @Override
    public Log getOrCreateLogForToday(Challenge challenge) {
        User user = userRepository.findById(challenge.getUser().getId()).orElse(null);
        LocalDate today = LocalDate.now();

        Challenge activeChallenge = challengeRepository.findChallengeByChallengeId(challenge);
        Optional<Log> existingLog = logRepository.findByChallengeAndDate(activeChallenge, today);
        int newLogDay = determineNewLogDay(activeChallenge);
        if (existingLog.isPresent()) {
            existingLog.get().setLogDay(newLogDay);
            return existingLog.get();
        } else {
            Log newLog = new Log();
            logRepository.save(newLog);
            // Set other default values as needed
            return newLog;
        }
    }

    /* CREATE OR UPDATE? (CREATE OR PUT) */
    @Override
    public void saveOrUpdateLog(Log log) {
        if (log.getId() == null) {
            // This is a new log
            logRepository.save(log);
        } else {
            // This is an existing log
            Log existingLog = logRepository.findById(log.getId())
                    .orElseThrow(() -> new RuntimeException("Log not found"));
            // Update the existing log with new values
            existingLog.setPushups(log.getPushups());
            existingLog.setSitups(log.getSitups());
            existingLog.setSquats(log.getSquats());
            existingLog.setPullups(log.getPullups());
            existingLog.setPlankMinutes(log.getPlankMinutes());
            existingLog.setRunMiles(log.getRunMiles());

            logRepository.save(existingLog);
        }
    }
}
