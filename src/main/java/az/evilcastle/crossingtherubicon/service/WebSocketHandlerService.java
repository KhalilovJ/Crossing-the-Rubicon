package az.evilcastle.crossingtherubicon.service;

import az.evilcastle.crossingtherubicon.mapper.GameSessionMapper;
import az.evilcastle.crossingtherubicon.model.constant.WebsocketMessageType;
import az.evilcastle.crossingtherubicon.model.dto.gamesession.LobbyDto;
import az.evilcastle.crossingtherubicon.model.dto.websocket.messaging.WebsocketMessageParent;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.SubProtocolCapable;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;


@Component
@Slf4j
@RequiredArgsConstructor
public class WebSocketHandlerService extends TextWebSocketHandler implements SubProtocolCapable {

    private final GameSessionService sessionService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Map<String, WebSocketSession> sessionMap = new HashMap<>();

    @PostConstruct
    private void init() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

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

        sessionMap.put(session.getId(), session);
        sendMessage(session, message);
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

        handleMessage(requestMessage, session);
    }

    private void handleMessage(WebsocketMessageParent message, WebSocketSession session) {
        switch (message.getRequestType()) {
            case CREATE_LOBBY ->
                    sendMessage(session, GameSessionMapper.INSTANCE.dtoToLobbyMessage(sessionService.createLobbyCommand(message)));
            case CONNECT_LOBBY -> connectLobbyEvent(message);
            case START_COMMAND -> sendLobby(sessionService.startCommandPressed(message));
        }
    }

    private void connectLobbyEvent(WebsocketMessageParent message) {
        sendLobby(sessionService.connectToLobbyCommand(message));
    }

    private void sendLobby(LobbyDto lobbyDto) {
        lobbyDto.players().forEach(playerDto ->
                Optional.ofNullable(sessionMap.get(playerDto.webSocketId()))
                        .ifPresent(webSocketSession -> sendMessage(webSocketSession, GameSessionMapper.INSTANCE.dtoToLobbyMessage(lobbyDto))));
    }

    public void sendMessage(WebSocketSession session, WebsocketMessageParent message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
            log.info("Message sent to: {}, message: {}", session.getId(), message.toString());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
