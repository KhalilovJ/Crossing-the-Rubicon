package az.evilcastle.crossingtherubicon.service;

import az.evilcastle.crossingtherubicon.dao.entity.WebsocketClientCollection;
import az.evilcastle.crossingtherubicon.dao.repository.WebSocketClientMongoRepository;
import az.evilcastle.crossingtherubicon.model.dto.PlayerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WebSocketLobbyService {

    private final WebSocketClientMongoRepository repository;



    public WebsocketClientCollection pairWebSocketLobby(String lobbyId, String socketId){
        WebsocketClientCollection websocketClientCollection = WebsocketClientCollection.builder()
                .lobbyId(lobbyId)
                .socketId(socketId).build();
        return repository.save(websocketClientCollection);
    }

    public WebsocketClientCollection pairSocketAndLobby(String lobbyId, PlayerDto player){
        return pairWebSocketLobby(lobbyId,player.webSocketId());
    }

    public Optional<WebsocketClientCollection> findByWebsocketId(String websocketId){
        return repository.findBySocketId(websocketId);
    }
}
