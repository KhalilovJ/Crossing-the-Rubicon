package az.evilcastle.crossingtherubicon.model.dto;

import java.io.Serializable;

public record PlayerDto(
        String username,
        String webSocketId
) implements Serializable {
}
