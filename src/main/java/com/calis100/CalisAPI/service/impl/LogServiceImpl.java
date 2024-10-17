package com.calis100.CalisAPI.service.impl;

import com.calis100.CalisAPI.model.Log;
import com.calis100.CalisAPI.model.User;
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
    public void saveLog(Log log) {
        logRepository.save(log);
    }

    @Override
    public List<Log> getLogsByUser(User user) {
        return logRepository.findAll();
    }
}
