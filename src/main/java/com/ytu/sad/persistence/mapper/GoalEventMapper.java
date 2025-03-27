package com.ytu.sad.persistence.mapper;

import com.ytu.sad.persistence.dto.GoalEventDTO;
import com.ytu.sad.persistence.entity.GoalEvent;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GoalEventMapper {

    @Named("toGoalEventDTO")
    @Mapping(target = "goalId", source = "id")
    @Mapping(target = "matchId", source = "match.id")
    @Mapping(target = "playerId", source = "scorer.id")
    @Mapping(target = "playerName", source = "scorer.name")
    @Mapping(target = "teamId", source = "team.id")
    @Mapping(target = "teamName", source = "team.name")
    @Mapping(target = "assistPlayerId", source = "assistBy.id")
    @Mapping(target = "assistPlayerName", source = "assistBy.name")
    @Mapping(target = "minute", source = "minute")
    GoalEventDTO toGoalEventDTO(GoalEvent goalEvent);

    @IterableMapping(qualifiedByName = "toGoalEventDTO")
    List<GoalEventDTO> toGoalEventDTOList(List<GoalEvent> goalEvents);

    @Mapping(target = "match", ignore = true)
    @Mapping(target = "scorer", ignore = true)
    @Mapping(target = "team", ignore = true)
    @Mapping(target = "assistBy", ignore = true)
    GoalEvent toGoalEvent(GoalEventDTO goalEventDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "match", ignore = true)
    @Mapping(target = "scorer", ignore = true)
    @Mapping(target = "team", ignore = true)
    @Mapping(target = "assistBy", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateGoalEventFromGoalEventDTO(GoalEventDTO goalEventDTO, @MappingTarget GoalEvent goalEvent);
}