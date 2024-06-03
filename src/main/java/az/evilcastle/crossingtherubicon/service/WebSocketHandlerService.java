package az.evilcastle.crossingtherubicon.service;

import az.evilcastle.crossingtherubicon.model.dto.websocket.messaging.WSCreateLobbyMessage;
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
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<String> getSubProtocols() {
        return Collections.emptyList();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("Server connection established; WebSessionId: {}", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.debug("Server connection closed: {}", status);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.debug("WebSocketSession message received; sessionId: {}; Message: {}", session.getId(), message.getPayload());

        var requestMessage = objectMapper.readValue(message.getPayload(), WebsocketMessageParent.class);
        requestMessage.setWebsocketId(session.getId());

        handleMessage(requestMessage);
    }

    private void handleMessage(WebsocketMessageParent message) {
        switch (message.getRequestType()) {
            case GET_LOBBIES -> {
            }
            case CREATE_LOBBY -> log.info(((WSCreateLobbyMessage) message).toString());
            case CONNECT_LOBBY -> {
            }
            case START_COMMAND -> {
            }
            case WEBSOCKET_CALLBACK -> {
            }
        }
    }
}
