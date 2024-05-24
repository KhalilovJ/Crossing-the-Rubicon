package az.evilcastle.crossingtherubicon.service;

import az.evilcastle.crossingtherubicon.dao.repository.GameSessionMongoRepository;
import az.evilcastle.crossingtherubicon.mapper.GameSessionMapper;
import az.evilcastle.crossingtherubicon.model.dto.GameSessionDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.List;

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
}