package az.evilcastle.crossingtherubicon.model.dto.gamesession;

import az.evilcastle.crossingtherubicon.model.constant.GameStatus;

import az.evilcastle.crossingtherubicon.model.dto.PlayerDto;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record GameSessionDto(
        String id,
        String name,
        String password,
        List<PlayerDto> players,
        GameStatus gameStatus

) {
}
