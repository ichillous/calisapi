package com.calis100.CalisAPI.service;

import com.calis100.CalisAPI.model.Challenge;
import com.calis100.CalisAPI.model.Log;

import java.util.List;

public interface LogService {
    Log saveLog(Log log);
    List<Log> getLogsByChallenge(Challenge challenge);
}
