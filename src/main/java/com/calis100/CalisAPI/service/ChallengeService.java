package com.calis100.CalisAPI.service;

import com.calis100.CalisAPI.model.Challenge;
import com.calis100.CalisAPI.model.User;
import com.calis100.CalisAPI.model.enums.Status;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

public interface ChallengeService {
    /* CRUD */
    Challenge startChallenge(Challenge challenge, User user);
    Challenge getActiveChallenge(User user);
    Challenge updateChallenge(Challenge challenge);
}