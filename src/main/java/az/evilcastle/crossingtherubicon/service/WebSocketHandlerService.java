package az.evilcastle.crossingtherubicon.service;
import az.evilcastle.crossingtherubicon.model.constant.WebsocketMessageType;
import az.evilcastle.crossingtherubicon.model.dto.gamesession.LobbyDto;
import az.evilcastle.crossingtherubicon.model.dto.websocket.messaging.WebsocketMessageParent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.SubProtocolCapable;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.Collections;
import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketHandlerService extends TextWebSocketHandler implements SubProtocolCapable {

    private final GameSessionService sessionService;
    private final WebSocketLobbyService webSocketLobbyService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<String> getSubProtocols() {
        return Collections.emptyList();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Server connection established; WebSessionId: {}", session.getId());

        var message = WebsocketMessageParent.builder()
                .websocketId(session.getId())
                .requestType(WebsocketMessageType.CONNECT_WEBSOCKET)
                .build();
        session.sendMessage(new TextMessage(message.toString()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("Server connection closed: {}", status);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("WebSocketSession message received; sessionId: {}; Message: {}", session.getId(), message.getPayload());

        var requestMessage = objectMapper.readValue(message.getPayload(), WebsocketMessageParent.class);
        requestMessage.setWebsocketId(session.getId());

        handleMessage(requestMessage);
    }

    private void handleMessage(WebsocketMessageParent message) {
        switch (message.getRequestType()) {
            case GET_LOBBIES -> {
            }
            case CREATE_LOBBY -> {
                sessionService.createLobbyCommand(message);
            }
            case CONNECT_LOBBY -> {
                sessionService.connectToLobbyCommand(message);
            }
            case START_COMMAND -> {
            }
            case WEBSOCKET_CALLBACK -> {
            }
        }
    }

}
