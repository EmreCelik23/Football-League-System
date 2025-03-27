package com.ytu.sad.persistence.mapper;

import com.ytu.sad.persistence.dto.CardEventDTO;
import com.ytu.sad.persistence.entity.CardEvent;
import com.ytu.sad.persistence.enums.CardTypeEnum;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CardEventMapper {

    @Named("toCardEventDTO")
    @Mapping(target = "cardId", source = "id")
    @Mapping(target = "matchId", source = "match.id")
    @Mapping(target = "teamId", source = "team.id")
    @Mapping(target = "teamName", source = "team.name")
    @Mapping(target = "playerId", source = "player.id")
    @Mapping(target = "playerName", source = "player.name")
    @Mapping(target = "cardType", source = "cardType")
    @Mapping(target = "minute", source = "minute")
    CardEventDTO toCardEventDTO(CardEvent cardEvent);

    @IterableMapping(qualifiedByName = "toCardEventDTO")
    List<CardEventDTO> toCardEventDTOList(List<CardEvent> cardEvents);

    @Mapping(target = "match", ignore = true)
    @Mapping(target = "team", ignore = true)
    @Mapping(target = "player", ignore = true)
    CardEvent toCardEvent(CardEventDTO cardEventDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "match", ignore = true)
    @Mapping(target = "player", ignore = true)
    @Mapping(target = "team", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCardEventFromCardEventDTO(CardEventDTO cardEventDTO, @MappingTarget CardEvent cardEvent);

    default List<CardEventDTO> filterYellowCards(List<CardEvent> cards) {
        return cards.stream()
                .filter(card -> card.getCardType() == CardTypeEnum.YELLOW)
                .map(this::toCardEventDTO)
                .toList();
    }

    default List<CardEventDTO> filterRedCards(List<CardEvent> cards) {
        return cards.stream()
                .filter(card -> card.getCardType() == CardTypeEnum.RED)
                .map(this::toCardEventDTO)
                .toList();
    }
}
