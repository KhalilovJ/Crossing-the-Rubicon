package az.evilcastle.crossingtherubicon.service;

import az.evilcastle.crossingtherubicon.dao.entity.WebSocketLobbyEntity;
import az.evilcastle.crossingtherubicon.dao.repository.WebSocketLobbyMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WebSocketLobbyService {

    private final WebSocketLobbyMongoRepository repository;



    public WebSocketLobbyEntity pairWebSocketLobby(String lobbyId,List<String> sockets){
        WebSocketLobbyEntity webSocketLobbyEntity = new WebSocketLobbyEntity(lobbyId,sockets);
        return repository.save(webSocketLobbyEntity);
    }
}
