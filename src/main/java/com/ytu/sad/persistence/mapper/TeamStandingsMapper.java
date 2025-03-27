package com.ytu.sad.persistence.mapper;

import com.ytu.sad.persistence.dto.TeamStandingsDTO;
import com.ytu.sad.persistence.entity.TeamStandings;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamStandingsMapper {

    @Named("toTeamStandingsDTO")
    @Mapping(target = "teamId", source = "team.id")
    @Mapping(target = "teamName", source = "team.name")
    @Mapping(target = "goalDifference", expression = "java(teamStandings.getGoalDifference())")
    @Mapping(target = "points", expression = "java(teamStandings.getPoints())")
    TeamStandingsDTO toTeamStandingsDTO(TeamStandings teamStandings);

    @IterableMapping(qualifiedByName = "toTeamStandingsDTO")
    List<TeamStandingsDTO> toTeamStandingsDTOList(List<TeamStandings> teamStandings);

    @Mapping(target = "team", ignore = true)
    @Mapping(target = "goalDifference", ignore = true)
    @Mapping(target = "points", ignore = true)
    TeamStandings toTeamStandings(TeamStandingsDTO teamStandingsDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", ignore = true)
    @Mapping(target = "goalDifference", ignore = true)
    @Mapping(target = "points", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTeamStandingsFromDTO(TeamStandingsDTO teamStandingsDTO, @MappingTarget TeamStandings teamStandings);
}