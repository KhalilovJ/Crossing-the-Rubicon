package az.evilcastle.crossingtherubicon.service;

import az.evilcastle.crossingtherubicon.dao.entity.WebSocketLobbyEntity;
import az.evilcastle.crossingtherubicon.dao.repository.WebSocketLobbyMongoRepository;
import az.evilcastle.crossingtherubicon.model.dto.PlayerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WebSocketLobbyService {

    private final WebSocketLobbyMongoRepository repository;



    public WebSocketLobbyEntity pairWebSocketLobby(String lobbyId,String socketId){
        WebSocketLobbyEntity webSocketLobbyEntity = WebSocketLobbyEntity.builder()
                .lobbyId(lobbyId)
                .socketId(socketId).build();
        return repository.save(webSocketLobbyEntity);
    }

    public WebSocketLobbyEntity pairSocketAndLobby(String lobbyId, PlayerDto player){
        return pairWebSocketLobby(lobbyId,player.webSocketId());
    }
}
