package com.ytu.sad.service.impl;

import com.ytu.sad.exception.InvalidRequestException;
import com.ytu.sad.exception.ResourceNotFoundException;
import com.ytu.sad.persistence.dto.*;
import com.ytu.sad.persistence.entity.*;
import com.ytu.sad.persistence.enums.CardTypeEnum;
import com.ytu.sad.persistence.enums.MatchStatusEnum;
import com.ytu.sad.persistence.enums.PlayerRoleEnum;
import com.ytu.sad.persistence.mapper.*;
import com.ytu.sad.persistence.repository.*;
import com.ytu.sad.service.IMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MatchServiceImpl implements IMatchService {

    private final MatchMapper matchMapper;
    private final GoalEventMapper goalEventMapper;
    private final CardEventMapper cardEventMapper;
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final GoalEventRepository goalEventRepository;
    private final CardEventRepository cardEventRepository;
    private final SubstitutionEventMapper substitutionMapper;
    private final PlayerStatsRepository playerStatsRepository;
    private final SubstitutionEventRepository substitutionRepository;
    private final TeamStatsRepository teamStatsRepository;
    private final TeamStandingsRepository teamStandingsRepository;


    @Autowired
    public MatchServiceImpl(MatchMapper matchMapper, GoalEventMapper goalEventMapper, CardEventMapper cardEventMapper, MatchRepository matchRepository, TeamRepository teamRepository, PlayerRepository playerRepository, GoalEventRepository goalEventRepository, CardEventRepository cardEventRepository, SubstitutionEventMapper substitutionMapper, PlayerStatsRepository playerStatsRepository, SubstitutionEventRepository substitutionRepository, TeamStatsRepository teamStatsRepository, TeamStandingsRepository teamStandingsRepository) {
        this.matchMapper = matchMapper;
        this.goalEventMapper = goalEventMapper;
        this.cardEventMapper = cardEventMapper;
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.goalEventRepository = goalEventRepository;
        this.cardEventRepository = cardEventRepository;
        this.substitutionMapper = substitutionMapper;
        this.playerStatsRepository = playerStatsRepository;
        this.substitutionRepository = substitutionRepository;
        this.teamStatsRepository = teamStatsRepository;
        this.teamStandingsRepository = teamStandingsRepository;
    }

    @Override
    public List<MatchDTO> getAllMatches() {
        List<Match> matches = matchRepository.findAll();
        return matchMapper.toMatchDTOList(matches);
    }

    @Override
    public MatchDTO getMatchById(Integer matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match with ID : " + matchId + " is not exist. "));
        return matchMapper.toMatchDTO(match);
    }

    @Override
    public MatchDTO createMatch(MatchDTO matchDTO) {
        Team homeTeam = teamRepository.findById(matchDTO.getHomeTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Home Team with ID: " + matchDTO.getHomeTeamId() + " is not exist ."));
        Team awayTeam = teamRepository.findById(matchDTO.getAwayTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Away Team with ID: " + matchDTO.getAwayTeamId() + " is not exist"));
        if (homeTeam == awayTeam){
            throw new InvalidRequestException("Home Team and Away Team cannot be the same.");
        }
        Match match = matchMapper.toMatch(matchDTO);
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);
        if (match.getStatus() == null) {
            match.setStatus(MatchStatusEnum.SCHEDULED);
        }
        matchRepository.save(match);
        return matchMapper.toMatchDTO(match);
    }

    @Override
    public MatchDTO updateMatch(Integer matchId, MatchDTO matchDTO) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match with ID : " + matchId + " isn't exist."));
        Team homeTeam = teamRepository.findById(matchDTO.getHomeTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Home Team with ID: " + matchDTO.getHomeTeamId() + " is not exist"));
        Team awayTeam = teamRepository.findById(matchDTO.getAwayTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Away Team with ID: " + matchDTO.getAwayTeamId() + " is not exist."));
        matchMapper.updateMatchFromMatchDTO(matchDTO, match);
        match.setHomeTeam(homeTeam);
        match.setAwayTeam(awayTeam);
        matchRepository.save(match);
        return matchMapper.toMatchDTO(match);
    }

    @Override
    public void deleteMatch(Integer matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match with ID:" + matchId + " is not exist."));
        matchRepository.delete(match);
    }

    @Override
    public MatchDTO addGoal(Integer matchId, GoalEventDTO goalEventDTO) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match with ID:" + matchId + " isn't exist."));

        if (!matchId.equals(goalEventDTO.getMatchId())) {
            throw new InvalidRequestException("Path Variable Match Id and Request Field Match Id are not the same.");
        }
        if (match.getStatus() != MatchStatusEnum.IN_PROGRESS){
            throw new InvalidRequestException("Match status isn't 'IN_PROGRESS'.");
        }

        Team team = teamRepository.findById(goalEventDTO.getTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team with ID : " + goalEventDTO.getTeamId() + " doesn't exist "));
        Player scorer = playerRepository.findById(goalEventDTO.getPlayerId())
                .orElseThrow(() -> new ResourceNotFoundException("Player with ID : " + goalEventDTO.getPlayerId() + " doesn't exist."));

        if (!team.getPlayers().contains(scorer)) {
            throw new InvalidRequestException("Player with id:" + scorer.getId() + " does not belong to Team with ID:" + team.getId());
        }

        if (!Objects.equals(match.getHomeTeam().getId(), team.getId()) &&
                !Objects.equals(match.getAwayTeam().getId(), team.getId())) {
            throw new InvalidRequestException("Team with id:" + team.getId() + " is not part of Match with ID:" + matchId);
        }

        GoalEvent goal = goalEventMapper.toGoalEvent(goalEventDTO);
        goal.setTeam(team);
        goal.setScorer(scorer);
        goal.setMatch(match);

        match.getGoals().add(goal);

        PlayerStats playerStats = playerStatsRepository.findByPlayerId(scorer.getId());
        TeamStats teamStats = teamStatsRepository.findByTeamId(team.getId());
        TeamStandings standings = teamStandingsRepository.findByTeamId(team.getId());

        if (goalEventDTO.getAssistPlayerId() != null) {
            Player assistPlayer = playerRepository.findById(goalEventDTO.getAssistPlayerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Player with ID : " + goalEventDTO.getAssistPlayerId() + " does not exist"));
            assistPlayer.setAssists(assistPlayer.getAssists() + 1);
            PlayerStats assistPlayerStats = playerStatsRepository.findByPlayerId(assistPlayer.getId());
            assistPlayerStats.setAssists(assistPlayerStats.getAssists() + 1);
            teamStats.setAssists(teamStats.getAssists() + 1);
            goal.setAssistBy(assistPlayer);
            playerRepository.save(assistPlayer);
            playerStatsRepository.save(assistPlayerStats);
        }

        if (Objects.equals(team.getId(), match.getHomeTeam().getId())) {
            match.setHomeScore(match.getHomeScore() + 1);
        } else {
            match.setAwayScore(match.getAwayScore() + 1);
        }

        scorer.setGoals(scorer.getGoals() + 1);
        playerStats.setGoals(playerStats.getGoals() + 1);
        teamStats.setGoalsScored(teamStats.getGoalsScored() + 1);
        standings.setGoalsScored(standings.getGoalsScored() + 1);

        Integer teamGoalConcededId = Objects.equals(goal.getTeam().getId(), match.getHomeTeam().getId()) ? match.getAwayTeam().getId() : match.getHomeTeam().getId();
        TeamStats teamGoalConcededStats= teamStatsRepository.findByTeamId(teamGoalConcededId);
        teamGoalConcededStats.setGoalsConceded(teamGoalConcededStats.getGoalsConceded() + 1);
        TeamStandings teamGoalConcededStandings = teamStandingsRepository.findByTeamId(teamGoalConcededId);
        teamGoalConcededStandings.setGoalsConceded(teamGoalConcededStandings.getGoalsConceded() + 1);

        playerRepository.save(scorer);
        matchRepository.save(match);
        goalEventRepository.save(goal);
        playerStatsRepository.save(playerStats);
        teamStatsRepository.save(teamStats);
        teamStatsRepository.save(teamGoalConcededStats);
        teamStandingsRepository.save(standings);
        teamStandingsRepository.save(teamGoalConcededStandings);

        return matchMapper.toMatchDTO(match);
    }

    @Override
    public MatchDTO addCard(Integer matchId, CardEventDTO cardEventDTO) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match with id : " + matchId + " does not exist. "));

        if (!matchId.equals(cardEventDTO.getMatchId())) {
            throw new InvalidRequestException("Path Variable Match id and Request Field Match id are not the same.");
        }
        if (match.getStatus() != MatchStatusEnum.IN_PROGRESS){
            throw new InvalidRequestException("Match status isn't 'IN_PROGRESS'.");
        }

        Team team = teamRepository.findById(cardEventDTO.getTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team with ID:" + cardEventDTO.getTeamId() + " does not exist"));
        Player player = playerRepository.findById(cardEventDTO.getPlayerId())
                .orElseThrow(() -> new ResourceNotFoundException("Player with ID:" + cardEventDTO.getPlayerId() + " does not exist. "));

        if (!team.getPlayers().contains(player)) {
            throw new InvalidRequestException("Player with ID:" + player.getId() + " doesn't belong to Team with ID: " + team.getId());
        }

        if (!Objects.equals(match.getHomeTeam().getId(), team.getId()) &&
                !Objects.equals(match.getAwayTeam().getId(), team.getId())) {
            throw new InvalidRequestException("Team with ID:" + team.getId() + " is not part of Match with id: " + matchId);
        }

        CardEvent card = cardEventMapper.toCardEvent(cardEventDTO);
        card.setTeam(team);
        card.setPlayer(player);
        card.setMatch(match);

        PlayerStats playerStats = playerStatsRepository.findByPlayerId(player.getId());
        TeamStats teamStats = teamStatsRepository.findByTeamId(team.getId());

        if (card.getCardType().equals(CardTypeEnum.YELLOW)){
            match.getYellowCards().add(card);
            player.setYellowCards(player.getYellowCards() + 1);
            playerStats.setYellowCards(playerStats.getYellowCards() + 1);
            teamStats.setYellowCards(teamStats.getYellowCards() + 1);
        } else if (card.getCardType().equals(CardTypeEnum.RED)) {
            match.getRedCards().add(card);
            player.setRedCards(player.getRedCards() + 1);
            playerStats.setRedCards(playerStats.getRedCards() + 1);
            teamStats.setRedCards(teamStats.getRedCards() + 1);
        }

        playerRepository.save(player);
        cardEventRepository.save(card);
        matchRepository.save(match);
        playerStatsRepository.save(playerStats);
        teamStatsRepository.save(teamStats);

        return matchMapper.toMatchDTO(match);
    }

    @Override
    public void deleteGoal(Integer matchId, Integer goalId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match with id: " + matchId + " doesn't exist. "));
        if (match.getStatus() != MatchStatusEnum.IN_PROGRESS){
            throw new InvalidRequestException("Match status is not 'IN_PROGRESS'.");
        }

        GoalEvent goal = goalEventRepository.findById(goalId)
                .orElseThrow(() -> new ResourceNotFoundException("Goal with ID: " + goalId + " doesn't exist."));

        Team team = goal.getTeam();
        Player player = goal.getScorer();
        player.setGoals(player.getGoals() - 1);

        if (Objects.equals(team.getId(), match.getHomeTeam().getId())) {
            match.setHomeScore(match.getHomeScore() - 1);
        } else {
            match.setAwayScore(match.getAwayScore() - 1);
        }

        PlayerStats playerStats = playerStatsRepository.findByPlayerId(player.getId());
        playerStats.setGoals(playerStats.getGoals() - 1);

        TeamStats teamStats = teamStatsRepository.findByTeamId(team.getId());
        teamStats.setGoalsScored(teamStats.getGoalsScored() - 1);

        TeamStandings standings = teamStandingsRepository.findByTeamId(team.getId());
        standings.setGoalsScored(standings.getGoalsScored() - 1);

        Integer teamGoalConcededId = Objects.equals(goal.getTeam().getId(), match.getHomeTeam().getId()) ? match.getAwayTeam().getId() : match.getHomeTeam().getId();
        TeamStats teamGoalConcededStats= teamStatsRepository.findByTeamId(teamGoalConcededId);
        teamGoalConcededStats.setGoalsConceded(teamGoalConcededStats.getGoalsConceded() - 1);
        TeamStandings teamGoalConcededStandings = teamStandingsRepository.findByTeamId(teamGoalConcededId);
        teamGoalConcededStandings.setGoalsConceded(teamGoalConcededStandings.getGoalsConceded() - 1);

        if (goal.getAssistBy() != null){
            Player assistPlayer = goal.getAssistBy();
            assistPlayer.setAssists(assistPlayer.getAssists() - 1);
            PlayerStats assistPlayerStats = playerStatsRepository.findByPlayerId(assistPlayer.getId());
            assistPlayerStats.setAssists(assistPlayerStats.getAssists() - 1);
            teamStats.setAssists(teamStats.getAssists() - 1);
            playerRepository.save(assistPlayer);
            playerStatsRepository.save(assistPlayerStats);
        }

        matchRepository.save(match);
        playerRepository.save(player);
        goalEventRepository.delete(goal);
        playerStatsRepository.save(playerStats);
        teamStatsRepository.save(teamStats);
        teamStatsRepository.save(teamGoalConcededStats);
        teamStandingsRepository.save(standings);
        teamStandingsRepository.save(teamGoalConcededStandings);
    }

    @Override
    public void deleteCard(Integer matchId, Integer cardId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match with id: " + matchId + " doesn't exist. "));
        if (match.getStatus() != MatchStatusEnum.IN_PROGRESS){
            throw new InvalidRequestException("Match status is not 'IN_PROGRESS'.");
        }

        CardEvent card = cardEventRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Card with ID: " + cardId + " can not found."));
        Player player = card.getPlayer();

        PlayerStats playerStats = playerStatsRepository.findByPlayerId(player.getId());
        TeamStats teamStats = teamStatsRepository.findByTeamId(card.getTeam().getId());

        if (card.getCardType().equals(CardTypeEnum.YELLOW)){
            player.setYellowCards(player.getYellowCards() - 1);
            playerStats.setYellowCards(playerStats.getYellowCards() - 1);
            teamStats.setYellowCards(teamStats.getYellowCards() - 1);
        } else if (card.getCardType().equals(CardTypeEnum.RED)) {
            player.setRedCards(player.getRedCards() - 1);
            playerStats.setRedCards(playerStats.getRedCards() - 1);
            teamStats.setRedCards(teamStats.getRedCards() - 1);
        }

        playerRepository.save(player);
        playerStatsRepository.save(playerStats);
        teamStatsRepository.save(teamStats);
        cardEventRepository.delete(card);
    }

    @Override
    public LineupsDTO getMatchLineups(Integer matchId) {
        LineupsDTO matchLineups = new LineupsDTO();
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match with Id: " + matchId + " does not exist . "));

        Team homeTeam = match.getHomeTeam();
        Team awayTeam = match.getAwayTeam();

        matchLineups.setMatchId(match.getId());
        matchLineups.setHomeTeamId(match.getHomeTeam().getId());
        matchLineups.setHomeTeamStartingIDs(homeTeam.getStartingPlayers().stream().map(Player::getId).toList());
        matchLineups.setHomeTeamReservesIDs(homeTeam.getReservePlayers().stream().map(Player::getId).toList());
        matchLineups.setAwayTeamId(match.getAwayTeam().getId());
        matchLineups.setAwayTeamStartingIDs(awayTeam.getStartingPlayers().stream().map(Player::getId).toList());
        matchLineups.setAwayTeamReservesIDs(awayTeam.getReservePlayers().stream().map(Player::getId).toList());

        return matchLineups;
    }

    @Override
    public LineupsDTO setMatchLineups(Integer matchId, LineupsDTO matchLineups) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match with Id: " + matchId + " does not exist "));

        if (!matchId.equals(matchLineups.getMatchId())) {
            throw new InvalidRequestException("Path Variable Match ID and Request Field Match ID are not the same.");
        }
        if (match.getStatus() != MatchStatusEnum.SCHEDULED){
            throw new InvalidRequestException("Match status is not 'SCHEDULED'.");
        }

        Team homeTeam = teamRepository.findById(matchLineups.getHomeTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team with id: " + matchLineups.getHomeTeamId() + " does not exist "));

        Team awayTeam = teamRepository.findById(matchLineups.getAwayTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team with Id: " + matchLineups.getAwayTeamId() + " doesn't exist"));

        if (!Objects.equals(match.getHomeTeam().getId(), homeTeam.getId())) {
            throw new InvalidRequestException("Team with id: " + homeTeam.getId() + " is not the home team of the match with ID: " + matchId);
        }

        if (!Objects.equals(match.getHomeTeam().getId(), awayTeam.getId())) {
            throw new InvalidRequestException("Team with Id: " + homeTeam.getId() + " is not the away team of the match with ID: " + matchId);
        }

        if (matchLineups.getHomeTeamStartingIDs().size() != 11 || matchLineups.getAwayTeamStartingIDs().size() != 11) {
            throw new InvalidRequestException("There must be 11 player in starting players for each team.");
        }

        if (matchLineups.getHomeTeamReservesIDs().size() > 10 || matchLineups.getAwayTeamReservesIDs().size() > 10) {
            throw new InvalidRequestException("There can be maximum 10 player in reserve players for each team.");
        }

        assignPlayerRoles(homeTeam, matchLineups.getHomeTeamStartingIDs(), PlayerRoleEnum.STARTING);
        assignPlayerRoles(awayTeam, matchLineups.getAwayTeamStartingIDs(), PlayerRoleEnum.STARTING);
        assignPlayerRoles(homeTeam, matchLineups.getHomeTeamReservesIDs(), PlayerRoleEnum.RESERVE);
        assignPlayerRoles(awayTeam, matchLineups.getAwayTeamReservesIDs(), PlayerRoleEnum.RESERVE);

        return getMatchLineups(match.getId());
    }

    private void assignPlayerRoles(Team team, List<Integer> playerIDs, PlayerRoleEnum role) {
        for (Player player : team.getPlayers()) {
            player.setRole(PlayerRoleEnum.NOT_IN_SQUAD);
        }

        for (Integer id : playerIDs) {
            Player player = playerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Player with id: " + id + " does not exist ."));

            if (!player.getTeam().equals(team)) {
                throw new InvalidRequestException("Player with Id: " + id + " does not belong to team ID: " + team.getId());
            }

            if (player.getRole() != PlayerRoleEnum.NOT_IN_SQUAD) {
                throw new InvalidRequestException("Player with Id: " + id + " in Starting 11 and Reserve players at the same time.");
            }

            player.setRole(role);
            playerRepository.save(player);
        }
    }

    @Override
    public MatchDTO addSubstitution(Integer matchId, SubstitutionEventDTO substitutionDTO) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match with ID :" + matchId + " does not exist ."));

        if (!matchId.equals(substitutionDTO.getMatchId())) {
            throw new InvalidRequestException("Path Variable Match ID and Request Field Match ID are not the same.");
        }
        if (match.getStatus() != MatchStatusEnum.IN_PROGRESS){
            throw new InvalidRequestException("Match status is not 'IN_PROGRESS'");
        }

        Team team = teamRepository.findById(substitutionDTO.getTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team with ID: " + substitutionDTO.getTeamId() + " does not exist . "));

        Player playerOut = playerRepository.findById(substitutionDTO.getOutPlayerId())
                .orElseThrow(() -> new ResourceNotFoundException("Player-Out with ID: " + substitutionDTO.getOutPlayerId() + " can not found."));

        Player playerIn = playerRepository.findById(substitutionDTO.getInPlayerId())
                .orElseThrow(() -> new ResourceNotFoundException("Player-In with ID: " + substitutionDTO.getOutPlayerId() + " can not found. "));

        if (!team.getPlayers().contains(playerIn)) {
            throw new InvalidRequestException("Player with id:" + playerIn.getId() + " does not belong to Team with ID: " + team.getId());
        }
        if (!team.getPlayers().contains(playerOut)) {
            throw new InvalidRequestException("Player with id: " + playerOut.getId() + " does not belong to Team with ID: " + team.getId());
        }
        if (!Objects.equals(match.getHomeTeam().getId(), team.getId()) &&
                !Objects.equals(match.getAwayTeam().getId(), team.getId())) {
            throw new InvalidRequestException("Team with ID: " + team.getId() + " is not part of Match with ID: " + matchId);
        }
        if (!team.getReservePlayers().contains(playerIn)) {
            throw new InvalidRequestException("Player with ID: " + playerIn.getId() + " is not in the reserves in Match with ID: " + matchId);
        }

        SubstitutionEvent substitution = substitutionMapper.toSubstitutionEvent(substitutionDTO);
        substitution.setMatch(match);
        substitution.setTeam(team);
        substitution.setOutPlayer(playerOut);
        substitution.setInPlayer(playerIn);

        PlayerStats playerOutStats = playerStatsRepository.findByPlayerId(playerOut.getId());
        playerOutStats.setMinutesPlayed(playerOutStats.getMinutesPlayed() + substitution.getMinute());
        PlayerStats playerInStats = playerStatsRepository.findByPlayerId(playerIn.getId());
        playerInStats.setMinutesPlayed(playerInStats.getMinutesPlayed() + 90 - substitution.getMinute());

        playerOut.setRole(PlayerRoleEnum.RESERVE);
        playerIn.setRole(PlayerRoleEnum.STARTING);

        playerRepository.save(playerOut);
        playerRepository.save(playerIn);
        playerStatsRepository.save(playerOutStats);
        playerStatsRepository.save(playerInStats);
        substitutionRepository.save(substitution);

        return matchMapper.toMatchDTO(match);
    }

    @Override
    public void deleteSubstitution(Integer matchId, Integer substitutionId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match with ID :" + matchId + " doesnt exist."));
        if (match.getStatus() != MatchStatusEnum.IN_PROGRESS){
            throw new InvalidRequestException("Match status is not 'IN_PROGRESS'");
        }

        SubstitutionEvent substitution = substitutionRepository.findById(substitutionId)
                .orElseThrow(() -> new ResourceNotFoundException("Substitution with ID: " + substitutionId + " does not exist  "));
        if (!Objects.equals(match.getId(), substitution.getId())){
            throw new InvalidRequestException("Substitution with ID: " + substitutionId + " is not part of Match with ID: " + match.getId());
        }

        Player playerOut = substitution.getOutPlayer();
        Player playerIn = substitution.getInPlayer();

        PlayerStats playerOutStats = playerStatsRepository.findByPlayerId(playerOut.getId());
        playerOutStats.setMinutesPlayed(playerOutStats.getMinutesPlayed() - substitution.getMinute());
        PlayerStats playerInStats = playerStatsRepository.findByPlayerId(playerIn.getId());
        playerInStats.setMinutesPlayed(playerInStats.getMinutesPlayed() - 90 + substitution.getMinute());

        playerOut.setRole(PlayerRoleEnum.STARTING);
        playerIn.setRole(PlayerRoleEnum.STARTING);

        playerRepository.save(playerOut);
        playerRepository.save(playerIn);
        playerStatsRepository.save(playerOutStats);
        playerStatsRepository.save(playerInStats);
        substitutionRepository.delete(substitution);
    }

    @Override
    public MatchDTO startMatch(Integer matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match with ID: " + matchId + " does not exist."));

        if (match.getStatus() == MatchStatusEnum.FINISHED){
            throw new InvalidRequestException("Match is already finished.");
        }
        if (match.getStatus() == MatchStatusEnum.IN_PROGRESS){
            throw new InvalidRequestException("Match is already in progress.");
        }
        if (match.getHomeTeam().getStartingPlayers().size() != 11 || match.getAwayTeam().getStartingPlayers().size() != 11){
            throw new InvalidRequestException("There must be 11 player in starting players for each team.");
        }
        if (match.getHomeTeam().getReservePlayers().size() > 10 || match.getAwayTeam().getReservePlayers().size() > 10){
            throw new InvalidRequestException("There can be maximum 10 player in reserve players for each team.");
        }

        match.setStatus(MatchStatusEnum.IN_PROGRESS);
        matchRepository.save(match);

        return matchMapper.toMatchDTO(match);
    }

    @Override
    public MatchDTO finishMatch(Integer matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match with ID: " + matchId + " does not exist."));

        if (match.getStatus() == MatchStatusEnum.FINISHED){
            throw new InvalidRequestException("Match is already finished.");
        }
        if (match.getStatus() == MatchStatusEnum.SCHEDULED){
            throw new InvalidRequestException("Match is not started yet.");
        }

        addMinuteAfterMatch(match, match.getHomeTeam().getStartingPlayers());
        addMinuteAfterMatch(match, match.getAwayTeam().getStartingPlayers());
        backToStandardLineups(match);
        updateStatsAndStandings(match);

        match.setStatus(MatchStatusEnum.FINISHED);
        matchRepository.save(match);

        return matchMapper.toMatchDTO(match);
    }

    private void addMinuteAfterMatch(Match match, List<Player> players){
        for (Player player : players){
            boolean hasSubstituted = false;
            for (SubstitutionEvent subs : match.getSubstitutions()){
                if (player == subs.getInPlayer()) {
                    hasSubstituted = true;
                    break;
                }
            }
            if (!hasSubstituted){
                PlayerStats playerStats = playerStatsRepository.findByPlayerId(player.getId());
                playerStats.setMinutesPlayed(playerStats.getMinutesPlayed() + 90);
                playerStats.setMatchesPlayed(playerStats.getMatchesPlayed() + 1);
            }
        }
    }

    private void backToStandardLineups(Match match){
        List<SubstitutionEvent> subs = match.getSubstitutions();
        for (SubstitutionEvent sub : subs){
            sub.getOutPlayer().setRole(PlayerRoleEnum.STARTING);
            sub.getInPlayer().setRole(PlayerRoleEnum.RESERVE);
        }
    }

    private void updateStatsAndStandings(Match match) {
        TeamStats homeStats = teamStatsRepository.findByTeamId(match.getHomeTeam().getId());
        TeamStats awayStats = teamStatsRepository.findByTeamId(match.getAwayTeam().getId());
        TeamStandings homeStandings = teamStandingsRepository.findByTeamId(match.getHomeTeam().getId());
        TeamStandings awayStandings = teamStandingsRepository.findByTeamId(match.getAwayTeam().getId());

        homeStats.setMatchesPlayed(homeStats.getMatchesPlayed() + 1);
        awayStats.setMatchesPlayed(awayStats.getMatchesPlayed() + 1);
        homeStandings.setMatchesPlayed(homeStandings.getMatchesPlayed() + 1);
        awayStandings.setMatchesPlayed(awayStandings.getMatchesPlayed() + 1);

        if (match.getHomeScore() > match.getAwayScore()) {
            updateWinnerLoser(homeStats, homeStandings, awayStandings, match.getAwayScore());
        } else if (match.getHomeScore() < match.getAwayScore()) {
            updateWinnerLoser(awayStats, awayStandings, homeStandings, match.getHomeScore());
        } else {
            homeStats.setPoints(homeStats.getPoints() + 1);
            awayStats.setPoints(awayStats.getPoints() + 1);
            homeStandings.setPoints(homeStandings.getPoints() + 1);
            awayStandings.setPoints(awayStandings.getPoints() + 1);
            homeStandings.setDraws(homeStandings.getDraws() + 1);
            awayStandings.setDraws(awayStandings.getDraws() + 1);

            if (match.getHomeScore() == 0) {
                homeStats.setCleanSheets(homeStats.getCleanSheets() + 1);
                awayStats.setCleanSheets(awayStats.getCleanSheets() + 1);
            }
        }

        teamStatsRepository.save(homeStats);
        teamStatsRepository.save(awayStats);
        teamStandingsRepository.save(homeStandings);
        teamStandingsRepository.save(awayStandings);
    }

    private void updateWinnerLoser(TeamStats winnerStats, TeamStandings winnerStandings,
                                   TeamStandings loserStandings, int loserScore) {
        winnerStats.setPoints(winnerStats.getPoints() + 3);
        winnerStandings.setPoints(winnerStandings.getPoints() + 3);
        winnerStandings.setWins(winnerStandings.getWins() + 1);

        if (loserScore == 0) {
            winnerStats.setCleanSheets(winnerStats.getCleanSheets() + 1);
        }

        loserStandings.setLosses(loserStandings.getLosses() + 1);
        winnerStandings.setGoalDifference(winnerStandings.getGoalsScored() - winnerStandings.getGoalsConceded());
        loserStandings.setGoalDifference(loserStandings.getGoalsScored() - loserStandings.getGoalsConceded());
    }
}

