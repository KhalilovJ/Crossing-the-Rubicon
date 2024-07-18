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
public class WSStartSessionMessage extends WebsocketMessageParent {

    private String lobbyId;
    private boolean start;

    @Override
    public WebsocketMessageType getRequestType(){
        return WebsocketMessageType.START_COMMAND;
    }
}
