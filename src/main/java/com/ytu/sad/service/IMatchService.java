package com.ytu.sad.service;

import com.ytu.sad.persistence.dto.*;

import jakarta.validation.Valid;

import java.util.List;

public interface IMatchService {
    List<MatchDTO> getAllMatches();

    MatchDTO getMatchById(Integer matchId);

    MatchDTO createMatch(MatchDTO matchDTO);

    MatchDTO updateMatch(Integer matchId, MatchDTO matchDTO);

    void deleteMatch(Integer matchId);

    MatchDTO addGoal(Integer matchId, @Valid GoalEventDTO goalEventDTO);

    MatchDTO addCard(Integer matchId, @Valid CardEventDTO cardEventDTO);

    void deleteGoal(Integer matchId, Integer goalId);

    void deleteCard(Integer matchId, Integer cardId);

    LineupsDTO getMatchLineups(Integer matchId);

    LineupsDTO setMatchLineups(Integer matchId, LineupsDTO lineups);

    MatchDTO addSubstitution(Integer matchId, @Valid SubstitutionEventDTO substitution);

    void deleteSubstitution(Integer matchId, Integer substitutionId);

    MatchDTO startMatch(Integer matchId);

    MatchDTO finishMatch(Integer matchId);

    void resetAllMatches();
}
