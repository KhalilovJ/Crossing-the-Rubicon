package az.evilcastle.crossingtherubicon.mapper;

import az.evilcastle.crossingtherubicon.dao.entity.GameSessionEntity;
import az.evilcastle.crossingtherubicon.model.dto.GameSessionDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GameSessionMapper {

    GameSessionDto entityToDto(GameSessionEntity entity);

    GameSessionEntity dtoToEntity(GameSessionDto dto);
}
