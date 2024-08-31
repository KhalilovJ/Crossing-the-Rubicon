package az.evilcastle.crossingtherubicon.model.dto;

import java.io.Serializable;

public record PlayerDto(
        String username,
        String webSocketId,
        boolean started
) implements Serializable {

    public PlayerDto withStarted(boolean started) {
        return new PlayerDto(username(), webSocketId(), started);
    }
}
