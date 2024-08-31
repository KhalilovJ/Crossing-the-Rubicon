package az.evilcastle.crossingtherubicon.model.dto.websocket.messaging;

import az.evilcastle.crossingtherubicon.model.constant.GameStatus;
import az.evilcastle.crossingtherubicon.model.constant.WebsocketMessageType;
import az.evilcastle.crossingtherubicon.model.dto.PlayerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class WSLobbyMessage extends WebsocketMessageParent {
    private String lobbyId;
    private String lobbyName;
    private List<PlayerDto> players;
    private GameStatus gameStatus;
    private boolean hasPassword;

    @Override
    public WebsocketMessageType getRequestType(){
        return WebsocketMessageType.LOBBY_DATA;
    }
}
