package az.evilcastle.crossingtherubicon.service;

import az.evilcastle.crossingtherubicon.model.constant.WebsocketMessageType;
import az.evilcastle.crossingtherubicon.model.dto.PlayerDto;
import az.evilcastle.crossingtherubicon.model.dto.gamesession.ConnectToLobbyDto;
import az.evilcastle.crossingtherubicon.model.dto.gamesession.CreateGameSessionDto;
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

    private final GameSessionService sessionService;
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
                WSCreateLobbyMessage ws = (WSCreateLobbyMessage) message;
                CreateGameSessionDto dto = new CreateGameSessionDto(ws.getLobbyName(),ws.getPassword());
                PlayerDto test = new PlayerDto("ihateniggas",message.getWebsocketId());
                sessionService.createGameSession(dto,test);
                log.info(((WSCreateLobbyMessage) message).toString());}
            case CONNECT_LOBBY -> {
                WSCreateLobbyMessage ws = (WSCreateLobbyMessage) message;
                ConnectToLobbyDto dto = new ConnectToLobbyDto(ws.getLobbyName(), ws.getPassword());
                PlayerDto test = new PlayerDto("iloveniggas",message.getWebsocketId());
                sessionService.connectGameSession(test,dto);
                log.info(((WSCreateLobbyMessage) message).toString());
            }
            case START_COMMAND -> {
            }
            case WEBSOCKET_CALLBACK -> {
            }
        }
    }
}
