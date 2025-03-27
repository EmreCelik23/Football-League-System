package com.ytu.sad.persistence.mapper;

import com.ytu.sad.persistence.dto.SubstitutionEventDTO;
import com.ytu.sad.persistence.entity.SubstitutionEvent;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubstitutionEventMapper {

    @Named("toSubstitutionEventDTO")
    @Mapping(target = "substitutionId", source = "id")
    @Mapping(target = "matchId", source = "match.id")
    @Mapping(target = "teamId", source = "team.id")
    @Mapping(target = "outPlayerId", source = "outPlayer.id")
    @Mapping(target = "outPlayerName", source = "outPlayer.name")
    @Mapping(target = "inPlayerId", source = "inPlayer.id")
    @Mapping(target = "inPlayerName", source = "inPlayer.name")
    @Mapping(target = "minute", source = "minute")
    SubstitutionEventDTO toSubstitutionEventDTO(SubstitutionEvent substitutionEvent);


    @Mapping(target = "match", ignore = true)
    @Mapping(target = "team", ignore = true)
    @Mapping(target = "outPlayer", ignore = true)
    @Mapping(target = "inPlayer", ignore = true)
    SubstitutionEvent toSubstitutionEvent(SubstitutionEventDTO substitutionEventDTO);

    @IterableMapping(qualifiedByName = "toSubstitutionEventDTO")
    List<SubstitutionEventDTO> toSubstitutionEventDTOList(List<SubstitutionEvent> substitutionEventList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "match", ignore = true)
    @Mapping(target = "team", ignore = true)
    @Mapping(target = "outPlayer", ignore = true)
    @Mapping(target = "inPlayer", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSubstitutionFromSubstitutionDTO(SubstitutionEventDTO substitutionEventDTO, @MappingTarget SubstitutionEvent substitutionEvent);
}
