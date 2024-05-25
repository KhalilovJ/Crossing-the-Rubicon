package az.evilcastle.crossingtherubicon.service;

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
public class WebSocketHandlerService extends TextWebSocketHandler implements SubProtocolCapable {
    @Override
    public List<String> getSubProtocols() {
        return Collections.emptyList();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Server connection established; WebSessionId: {}", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        log.info("Server connection closed: {}", status);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("WebSocketSession message received; sessionId: {}; Message: {}", session.getId(), message);

    }
}
