package az.evilcastle.crossingtherubicon.model.dto.gamesession;

import az.evilcastle.crossingtherubicon.model.constant.GameStatus;

import az.evilcastle.crossingtherubicon.model.dto.PlayerDto;
import lombok.Builder;

import java.util.List;

@Builder
public record LobbyDto(
        String id,
        String lobbyName,
        boolean hasPassword,
        List<PlayerDto> players,
        GameStatus gameStatus
) {
}
