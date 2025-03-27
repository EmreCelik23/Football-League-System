package com.ytu.sad.persistence.mapper;

import com.ytu.sad.persistence.dto.TeamDTO;
import com.ytu.sad.persistence.entity.Team;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    List<TeamDTO> toTeamDtoList(List<Team> teams);

    TeamDTO toTeamDto(Team team);

    Team toTeam(TeamDTO teamDTO);

    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTeamFromTeamDTO(TeamDTO teamDTO, @MappingTarget Team team);
}
