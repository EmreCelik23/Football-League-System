package com.ytu.sad.persistence.mapper;

import com.ytu.sad.persistence.dto.TeamStatsDTO;
import com.ytu.sad.persistence.entity.TeamStats;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamStatsMapper {

    @Named("toTeamStatsDTO")
    @Mapping(target = "teamId", source = "team.id")
    @Mapping(target = "teamName", source = "team.name")
    @Mapping(target = "pointsPerMatch", expression = "java(teamStats.getPointsPerMatch())")
    @Mapping(target = "goalsPerMatch", expression = "java(teamStats.getGoalsPerMatch())")
    @Mapping(target = "assistsPerMatch", expression = "java(teamStats.getAssistsPerMatch())")
    @Mapping(target = "goalsConcededPerMatch", expression = "java(teamStats.getGoalsConcededPerMatch())")
    @Mapping(target = "yellowCardsPerMatch", expression = "java(teamStats.getYellowCardsPerMatch())")
    @Mapping(target = "redCardsPerMatch", expression = "java(teamStats.getRedCardsPerMatch())")
    TeamStatsDTO toTeamStatsDTO(TeamStats teamStats);

    @IterableMapping(qualifiedByName = "toTeamStatsDTO")
    List<TeamStatsDTO> toTeamStatsDTOList(List<TeamStats> teamStatsList);

    @Mapping(target = "team", ignore = true)
    TeamStats toTeamStats(TeamStatsDTO teamStatsDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", ignore = true)
    @Mapping(target = "pointsPerMatch", ignore = true)
    @Mapping(target = "goalsPerMatch", ignore = true)
    @Mapping(target = "assistsPerMatch", ignore = true)
    @Mapping(target = "goalsConcededPerMatch", ignore = true)
    @Mapping(target = "yellowCardsPerMatch", ignore = true)
    @Mapping(target = "redCardsPerMatch", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTeamStatsFromDTO(TeamStatsDTO teamStatsDTO, @MappingTarget TeamStats teamStats);
}