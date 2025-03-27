package com.ytu.sad.persistence.mapper;

import com.ytu.sad.persistence.dto.MatchDTO;
import com.ytu.sad.persistence.entity.Match;
import org.mapstruct.*;

import java.util.List;


@Mapper(componentModel = "spring", uses = {GoalEventMapper.class, CardEventMapper.class, SubstitutionEventMapper.class})
public interface MatchMapper {

    @Named("toMatchDTO")
    @Mapping(target = "homeTeamId", source = "homeTeam.id")
    @Mapping(target = "homeTeamName", source = "homeTeam.name")
    @Mapping(target = "awayTeamId", source = "awayTeam.id")
    @Mapping(target = "awayTeamName", source = "awayTeam.name")
    @Mapping(target = "goals", source = "goals")
    @Mapping(target = "yellowCards", expression = "java(cardEventMapper.filterYellowCards(match.getYellowCards()))")
    @Mapping(target = "redCards", expression = "java(cardEventMapper.filterRedCards(match.getRedCards()))")
    @Mapping(target = "substitutions", source = "substitutions")
    MatchDTO toMatchDTO(Match match);

    @Mapping(target = "homeTeam", ignore = true)
    @Mapping(target = "awayTeam", ignore = true)
    Match toMatch(MatchDTO matchDTO);

    @IterableMapping(qualifiedByName = "toMatchDTO")
    List<MatchDTO> toMatchDTOList(List<Match> matchList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "homeTeam", ignore = true)
    @Mapping(target = "awayTeam", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMatchFromMatchDTO(MatchDTO matchDTO, @MappingTarget Match match);

}
