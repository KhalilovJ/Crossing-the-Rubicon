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
import az.evilcastle.crossingtherubicon.model.dto.gamesession.GameSessionDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.IterableUtils;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class GameSessionService {

    private final GameSessionMongoRepository gameSessionMongoRepository;

    private final GameSessionMapper gameSessionMapper;

    public List<GameSessionDto> getAllGameSessions() {
        log.debug("ActionLog.getAllGameSessions.start");

        var gameSessionEntities = gameSessionMongoRepository.findAll();
        var gameSessionsDtoList = IterableUtils.toList(gameSessionEntities).stream()
                .map(gameSessionMapper::entityToDto)
                .toList();

        log.debug("ActionLog.getAllGameSessions.end");
        return gameSessionsDtoList;
    }


    public GameSessionDto createGameSession(CreateGameSessionDto sessionDto, PlayerDto player){
        GameSessionEntity session = GameSessionEntity.builder()
                .id(UUID.randomUUID().toString())
                .sessionName(sessionDto.name())
                .password(sessionDto.password() != null ? sessionDto.password() : null)
                .status(GameStatus.WAITING)
                .players(Collections.singletonList(player))
                .build();
        log.info("👾Lobby: {} created by {}",session,player.username());
        gameSessionMongoRepository.save(session);
        return gameSessionMapper.entityToDto(session);
    }


    public GameSessionDto connectGameSession(PlayerDto player, ConnectToLobbyDto connect){
        GameSessionEntity lobby = gameSessionMongoRepository.findBySessionName(connect.name())
                .orElseThrow(()->new LobbyIsNotFound("Lobby is not found"));
        log.info("{} tried connect to lobby {}",player.username(),lobby.getSessionName());
        if (Objects.equals(connect.password(), lobby.getPassword())){
            List<PlayerDto> currentPlayers = lobby.getPlayers();
            if (currentPlayers.size()>=2){
                throw  new LobbyIsFullException("Lobby is full");
            }
            else {
                currentPlayers.add(player);
                lobby.setStatus(GameStatus.READY);
                gameSessionMongoRepository.save(lobby);
            }
        }
        else {
            throw new LobbyIsFullException("Password is wrong");
        }
        log.info(lobby);
        return gameSessionMapper.entityToDto(lobby);
    }
}