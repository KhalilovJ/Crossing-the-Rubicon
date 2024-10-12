package az.evilcastle.crossingtherubicon.model.dto.websocket.messaging;

import az.evilcastle.crossingtherubicon.model.constant.WebsocketMessageType;
import az.evilcastle.crossingtherubicon.model.dto.ActionType;
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
public class WSActionMessage extends WebsocketMessageParent {

    private String unitId;
    private List<String> cellId;
    private ActionType action;
    private UnitActionType actionType;

    @Override
    public WebsocketMessageType getRequestType() {
        return WebsocketMessageType.GAME_ACTION;
    }
}
