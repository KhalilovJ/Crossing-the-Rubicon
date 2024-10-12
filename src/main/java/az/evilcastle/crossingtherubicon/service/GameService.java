package az.evilcastle.crossingtherubicon.service;

import az.evilcastle.crossingtherubicon.mapper.GameSessionMapper;
import az.evilcastle.crossingtherubicon.model.dto.gamesession.LobbyDto;
import az.evilcastle.crossingtherubicon.model.dto.websocket.messaging.WebsocketMessageParent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameSessionService gameSessionService;

    public Pair<LobbyDto, WebsocketMessageParent> actionReceived(WebsocketMessageParent actionMessage){
        var lobby = GameSessionMapper.INSTANCE.entityToDto(gameSessionService.findPlayersLobby(actionMessage.getWebsocketId()));
        return Pair.of(lobby, actionMessage);
    }
}
