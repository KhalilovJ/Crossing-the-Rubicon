package az.evilcastle.crossingtherubicon.service;

import az.evilcastle.crossingtherubicon.dao.entity.GameSessionEntity;
import az.evilcastle.crossingtherubicon.dao.repository.GameSessionMongoRepository;
import az.evilcastle.crossingtherubicon.exceptions.LobbyIsFullException;
import az.evilcastle.crossingtherubicon.exceptions.LobbyIsNotFound;
import az.evilcastle.crossingtherubicon.mapper.GameSessionMapper;
import az.evilcastle.crossingtherubicon.model.constant.GameStatus;
import az.evilcastle.crossingtherubicon.model.dto.PlayerDto;
import az.evilcastle.crossingtherubicon.model.dto.gamesession.ConnectToLobbyDto;
import az.evilcastle.crossingtherubicon.model.dto.gamesession.CreateGameSessionDto;
import az.evilcastle.crossingtherubicon.model.dto.gamesession.LobbyDto;
import az.evilcastle.crossingtherubicon.model.dto.websocket.messaging.WSConnectLobbyMessage;
import az.evilcastle.crossingtherubicon.model.dto.websocket.messaging.WSCreateLobbyMessage;
import az.evilcastle.crossingtherubicon.model.dto.websocket.messaging.WSStartSessionMessage;
import az.evilcastle.crossingtherubicon.model.dto.websocket.messaging.WebsocketMessageParent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
public class GameSessionService {

    private final GameSessionMongoRepository gameSessionMongoRepository;

    private final GameSessionMapper gameSessionMapper;
    private final WebSocketLobbyService webSocketLobbyService;

    public List<LobbyDto> getAllGameSessions() {
        log.debug("ActionLog.getAllGameSessions.start");

        var gameSessionEntities = gameSessionMongoRepository.findAll();
        var gameSessionsDtoList = IterableUtils.toList(gameSessionEntities).stream()
                .map(gameSessionMapper::entityToDto)
                .toList();

        log.debug("ActionLog.getAllGameSessions.end");
        return gameSessionsDtoList;
    }


    public LobbyDto createLobby(CreateGameSessionDto sessionDto, PlayerDto player) {
        GameSessionEntity lobby = GameSessionEntity.builder()
                .id(UUID.randomUUID().toString())
                .lobbyName(sessionDto.name())
                .password(sessionDto.password() != null ? sessionDto.password() : null)
                .status(GameStatus.WAITING)
                .players(Collections.singletonList(player))
                .build();
        log.info("👾Lobby: {} created by {}", lobby, player.username());
        gameSessionMongoRepository.save(lobby);
        webSocketLobbyService.pairSocketAndLobby(lobby.getId(), lobby.getPlayers().get(0));
        return gameSessionMapper.entityToDto(lobby);
    }

    public LobbyDto connectToLobby(ConnectToLobbyDto connect, PlayerDto player) {
        GameSessionEntity lobby = findLobby(connect.lobbyId());
        log.info("{} tried connect to lobby {}", player.username(), lobby.getLobbyName());
        validateLobbyPassword(connect.password(), lobby);
        addPlayerToLobby(lobby, player);
        log.info("{} successfully connected to lobby {}", player.username(), lobby.getLobbyName());
        webSocketLobbyService.pairSocketAndLobby(lobby.getId(), lobby.getPlayers().get(1));
        return gameSessionMapper.entityToDto(lobby);
    }

    private GameSessionEntity findLobby(String lobbyId) {
        return gameSessionMongoRepository.findById(lobbyId)
                .orElseThrow(() -> new LobbyIsNotFound("Lobby is not found"));
    }

    private void validateLobbyPassword(String providedPassword, GameSessionEntity lobby) {
        if (!Objects.equals(providedPassword, lobby.getPassword())) {
            throw new LobbyIsFullException("Password is wrong");
        }
    }

    private void addPlayerToLobby(GameSessionEntity lobby, PlayerDto player) {
        List<PlayerDto> currentPlayers = lobby.getPlayers();
        if (currentPlayers.size() >= 2) {
            throw new LobbyIsFullException("Lobby is full");
        }
        currentPlayers.add(player);
        lobby.setStatus(GameStatus.READY);
        gameSessionMongoRepository.save(lobby);
    }


    public LobbyDto createLobbyCommand(WebsocketMessageParent message) {
        WSCreateLobbyMessage ws = (WSCreateLobbyMessage) message;
        CreateGameSessionDto lobby = new CreateGameSessionDto(ws.getLobbyName(), ws.getPassword());
        PlayerDto creator = new PlayerDto("ihateniggas", message.getWebsocketId(), false);
        log.info(((WSCreateLobbyMessage) message).toString());
        return createLobby(lobby, creator);
    }

    public LobbyDto connectToLobbyCommand(WebsocketMessageParent message) {
        WSConnectLobbyMessage ws = (WSConnectLobbyMessage) message;
        ConnectToLobbyDto lobby = new ConnectToLobbyDto(ws.getLobbyId(), ws.getPassword());
        PlayerDto connectingPlayer = new PlayerDto("iloveniggas", message.getWebsocketId(), false);
        log.info(((WSConnectLobbyMessage) message).toString());
        return connectToLobby(lobby, connectingPlayer);
    }

    public LobbyDto startCommandPressed(WebsocketMessageParent startMessage) {
        var message = (WSStartSessionMessage) startMessage;
        return webSocketLobbyService.findByWebsocketId(message.getWebsocketId())
                .map(client -> {
                    var lobby = findLobby(client.getLobbyId());
                    lobby.setPlayers(lobby.getPlayers()
                            .stream()
                            .map(playerDto -> {
                                if (playerDto.webSocketId().equals(message.getWebsocketId()))
                                    return playerDto.withStarted(message.isStart());
                                return playerDto;
                            }).toList());

                    if (checkEveryoneStarted(lobby.getPlayers())) {
                        lobby.setStatus(GameStatus.STARTED);
                    }
                    return GameSessionMapper.INSTANCE.entityToDto(gameSessionMongoRepository.save(lobby));
                }).orElseThrow(() -> new LobbyIsNotFound("Lobby of player " + message.getWebsocketId() + " is not found"));
    }

    private boolean checkEveryoneStarted(List<PlayerDto> players) {
        return players.stream()
                .map(PlayerDto::started)
                .filter(Boolean::booleanValue)
                .findFirst()
                .orElse(false);
    }
}