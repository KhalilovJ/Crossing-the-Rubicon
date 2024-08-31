package az.evilcastle.crossingtherubicon.mapper;

import az.evilcastle.crossingtherubicon.dao.entity.GameSessionEntity;
import az.evilcastle.crossingtherubicon.model.dto.gamesession.LobbyDto;
import az.evilcastle.crossingtherubicon.model.dto.websocket.messaging.WSLobbyMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Perform Clean Build when editing old mapper
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GameSessionMapper {
    GameSessionMapper INSTANCE = Mappers.getMapper(GameSessionMapper.class);

    @Mapping(target = "hasPassword", expression = "java(entity.getPassword() != null && !entity.getPassword().isEmpty())")
    @Mapping(target = "gameStatus", source = "entity.status")
    LobbyDto entityToDto(GameSessionEntity entity);

    GameSessionEntity dtoToEntity(LobbyDto dto);

    @Mapping(target = "lobbyId", source = "dto.id")
    WSLobbyMessage dtoToLobbyMessage(LobbyDto dto);
}
