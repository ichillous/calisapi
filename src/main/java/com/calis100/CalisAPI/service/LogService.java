package com.calis100.CalisAPI.service;

import com.calis100.CalisAPI.model.Challenge;
import com.calis100.CalisAPI.model.Log;
import com.calis100.CalisAPI.model.User;

import java.util.List;

public interface LogService {
    // Basic CRUD operations
    List<Log> findAllByChallenge(Challenge challenge);
    Log create(Log log);
    void updateLog(Long logId);
    void deleteById(Long id);
    List<Log> findByUser(Challenge challenge, List<Log> logs, User user);
    Log findById(Long id);
}


