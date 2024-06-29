package az.evilcastle.crossingtherubicon.config;

import az.evilcastle.crossingtherubicon.service.GameSessionService;
import az.evilcastle.crossingtherubicon.service.WebSocketHandlerService;
import az.evilcastle.crossingtherubicon.service.WebSocketLobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfiguration implements WebSocketConfigurer {

    private final GameSessionService sessionService;
    private final WebSocketLobbyService webSocketLobbyService;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/rubicon");
    }

    @Bean
    public WebSocketHandler webSocketHandler() {
        return new WebSocketHandlerService(sessionService);
    }
}
