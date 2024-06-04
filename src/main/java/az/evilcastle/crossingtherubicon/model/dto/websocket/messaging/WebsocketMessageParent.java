package az.evilcastle.crossingtherubicon.model.dto.websocket.messaging;

import az.evilcastle.crossingtherubicon.model.constant.WebsocketMessageType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
        @JsonSubTypes.Type(value = WSCreateLobbyMessage.class, name = "CREATE_LOBBY"),
})
public class WebsocketMessageParent {
    String websocketId;
    WebsocketMessageType requestType;
}
