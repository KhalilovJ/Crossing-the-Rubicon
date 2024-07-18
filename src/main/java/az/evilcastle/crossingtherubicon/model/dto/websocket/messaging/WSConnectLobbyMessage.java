package az.evilcastle.crossingtherubicon.model.dto.websocket.messaging;

import az.evilcastle.crossingtherubicon.model.constant.WebsocketMessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class WSConnectLobbyMessage extends WebsocketMessageParent{

    private String lobbyId;
    private String password;

    @Override
    public WebsocketMessageType getRequestType(){
        return WebsocketMessageType.CONNECT_LOBBY;
    }
}
