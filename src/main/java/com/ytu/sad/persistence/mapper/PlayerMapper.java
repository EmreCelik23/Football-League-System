package com.ytu.sad.persistence.mapper;

import com.ytu.sad.persistence.dto.PlayerDTO;
import com.ytu.sad.persistence.entity.Player;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    @Named("toPlayerDto")
    @Mapping(target = "teamId", source = "team.id")
    @Mapping(target = "teamName", source = "team.name")
    PlayerDTO toPlayerDto(Player player);

    @IterableMapping(qualifiedByName = "toPlayerDto")
    List<PlayerDTO> toPlayerDtoList(List<Player> players);

    @Mapping(target = "team", ignore = true)
    Player toPlayer(PlayerDTO playerDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "team", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePlayerFromPlayerDTO(PlayerDTO playerDTO, @MappingTarget Player player);
}