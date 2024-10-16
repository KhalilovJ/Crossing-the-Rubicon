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

import az.evilcastle.crossingtherubicon.model.dto.websocket.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.*;

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


    public LobbyDto createLobby(CreateGameSessionDto sessionDto, PlayerDto player){
        GameSessionEntity lobby = GameSessionEntity.builder()
                .id(UUID.randomUUID().toString())
                .lobbyName(sessionDto.name())
                .password(sessionDto.password() != null ? sessionDto.password() : null)
                .status(GameStatus.WAITING)
                .players(Collections.singletonList(player))
                .build();
        log.info("👾Lobby: {} created by {}",lobby,player.username());
        gameSessionMongoRepository.save(lobby);
        webSocketLobbyService.pairSocketAndLobby(lobby.getId(),lobby.getPlayers().get(0));
        return gameSessionMapper.entityToDto(lobby);
    }

    public LobbyDto connectToLobby(ConnectToLobbyDto connect, PlayerDto player) {
        GameSessionEntity lobby = findLobby(connect.lobbyId());
        log.info("{} tried connect to lobby {}", player.username(), lobby.getLobbyName());
        validateLobbyPassword(connect.password(), lobby);
        addPlayerToLobby(lobby, player);
        log.info("{} successfully connected to lobby {}",player.username(),lobby.getLobbyName());
        webSocketLobbyService.pairSocketAndLobby(lobby.getId(),lobby.getPlayers().get(1));
        return gameSessionMapper.entityToDto(lobby);
    }

    private GameSessionEntity findLobby(String lobbyName) {
        return gameSessionMongoRepository.findById(lobbyName)
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


    public LobbyDto createLobbyCommand(WebsocketMessageParent message){
        WSCreateLobbyMessage ws = (WSCreateLobbyMessage) message;
        CreateGameSessionDto lobby = new CreateGameSessionDto(ws.getLobbyName(),ws.getPassword());
        PlayerDto creator = new PlayerDto("ihateniggas",message.getWebsocketId());
        log.info(((WSCreateLobbyMessage) message).toString());
        return createLobby(lobby,creator);
    }

    public LobbyDto connectToLobbyCommand(WebsocketMessageParent message){
        WSConnectLobbyMessage ws = (WSConnectLobbyMessage) message;
        ConnectToLobbyDto lobby = new ConnectToLobbyDto(ws.getLobbyId(), ws.getPassword());
        PlayerDto connector = new PlayerDto("iloveniggas",message.getWebsocketId());
        log.info(((WSConnectLobbyMessage) message).toString());
        return connectToLobby(lobby,connector);
    }
}