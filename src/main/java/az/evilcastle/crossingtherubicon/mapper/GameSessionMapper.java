package az.evilcastle.crossingtherubicon.mapper;

import az.evilcastle.crossingtherubicon.dao.entity.GameSessionEntity;
import az.evilcastle.crossingtherubicon.model.dto.gamesession.LobbyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 *  Perform Clean Build when editing old mapper
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GameSessionMapper {

    @Mapping(target = "hasPassword", expression = "java(entity.getPassword() != null && !entity.getPassword().isEmpty())")
    LobbyDto entityToDto(GameSessionEntity entity);

    GameSessionEntity dtoToEntity(LobbyDto dto);
}
