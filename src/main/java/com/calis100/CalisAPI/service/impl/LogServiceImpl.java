package com.calis100.CalisAPI.service.impl;

import com.calis100.CalisAPI.model.Challenge;
import com.calis100.CalisAPI.model.Log;
import com.calis100.CalisAPI.repository.LogRepository;
import com.calis100.CalisAPI.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {
    private final LogRepository logRepository;

    @Autowired
    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public Log saveLog(Log log) {
        return logRepository.save(log);
    }

    @Override
    public List<Log> getLogsByChallenge(Challenge challenge) {
        return logRepository.findByChallenge(challenge);
    }
}
