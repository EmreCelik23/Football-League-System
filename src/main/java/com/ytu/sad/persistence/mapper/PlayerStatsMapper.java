package com.ytu.sad.persistence.mapper;

import com.ytu.sad.persistence.dto.PlayerStatsDTO;
import com.ytu.sad.persistence.entity.PlayerStats;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlayerStatsMapper {

    @Named("toPlayerStatsDTO")
    @Mapping(target = "playerId", source = "player.id")
    @Mapping(target = "playerName", source = "player.name")
    @Mapping(target = "goalsPerMatch", expression = "java(playerStats.getGoalsPerMatch())")
    @Mapping(target = "assistsPerMatch", expression = "java(playerStats.getAssistsPerMatch())")
    PlayerStatsDTO toPlayerStatsDTO(PlayerStats playerStats);

    @IterableMapping(qualifiedByName = "toPlayerStatsDTO")
    List<PlayerStatsDTO> toPlayerStatsDTOList(List<PlayerStats> playerStatsList);

    @Mapping(target = "player", ignore = true)
    PlayerStats toPlayerStats(PlayerStatsDTO playerStatsDTO);
}