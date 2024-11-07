package com.calis100.CalisAPI.service.impl;

import com.calis100.CalisAPI.model.Challenge;
import com.calis100.CalisAPI.model.Log;
import com.calis100.CalisAPI.model.User;
import com.calis100.CalisAPI.model.enums.Status;
import com.calis100.CalisAPI.repository.ChallengeRepository;
import com.calis100.CalisAPI.repository.LogRepository;
import com.calis100.CalisAPI.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service
@Transactional
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    @Autowired
    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public List<Log> findAllByChallenge(Challenge challenge) {
        return logRepository.findAll();
    }


    @Override
    public Log create(Log log) {
        if (log.getChallenge() == null) {
            throw new IllegalArgumentException("Challenge must not be null");
        }

        return logRepository.save(log);
    }

    @Override
    public void deleteById(Long id) {
        logRepository.deleteById(id);
    }

    @Override
    public void updateLog(Long logId) {
        Log log = logRepository.findById(logId).orElse(null);

        try {
            assert log != null;
            log.setLastUpdate(log.getLastUpdate());
            log.setPushups(log.getPushups());
            log.setSitups(log.getSitups());
            log.setPullups(log.getPullups());
            log.setSquats(log.getSquats());
            log.setPlankMinutes(log.getPlankMinutes());
            log.setRunMiles(log.getRunMiles());
        } catch (NullPointerException e) {
            e.getMessage();
        }
    }

    @Override
    public List<Log> findByUser (Challenge challenge, List<Log> logs, User user) {
        List<Log> logList = new ArrayList<>();
        if(challenge.getStatus() == Status.Active && challenge.getUser() == user){
            logList.addAll(logs);
        }
        return logList;
    }

    @Override
    public Log findById(Long id) {
        Log log;
        log = logRepository.findById(id).orElse(null);
        return log;
    }
}



