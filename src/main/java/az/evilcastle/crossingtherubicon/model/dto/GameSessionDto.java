package az.evilcastle.crossingtherubicon.model.dto;

import az.evilcastle.crossingtherubicon.model.constant.GameStatus;

import lombok.Builder;

import java.util.UUID;

@Builder
public record GameSessionDto(
        UUID id,
        GameStatus gameStatus
) {
}
