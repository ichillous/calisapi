package com.calis100.CalisAPI.service;

import com.calis100.CalisAPI.model.Challenge;
import com.calis100.CalisAPI.model.Log;
import com.calis100.CalisAPI.model.User;

import java.util.List;

public interface LogService {
    void saveLog(Log log);
    List<Log> getLogsByUser(User user);
    Log getLogByUserId(Long userId);
    Log getOrCreateLogForToday(Challenge challenge);
    void saveOrUpdateLog(Log log);
}
