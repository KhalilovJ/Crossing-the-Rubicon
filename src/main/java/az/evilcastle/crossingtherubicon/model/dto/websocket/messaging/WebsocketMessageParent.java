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
@JsonSubTypes({
        @JsonSubTypes.Type(value = WSCreateLobbyMessage.class, name = "WSCreateLobbyMessage")
})
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
public abstract class WebsocketMessageParent {
    String websocketId;
    WebsocketMessageType requestType;
}
