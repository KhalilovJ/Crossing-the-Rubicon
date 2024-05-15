
package az.evilcastle.crossingtherubicon.service.impl;

import az.evilcastle.crossingtherubicon.dao.repository.GameSessionRedisRepository;
import az.evilcastle.crossingtherubicon.mapper.GameSessionMapper;
import az.evilcastle.crossingtherubicon.model.dto.GameSessionDto;
import az.evilcastle.crossingtherubicon.service.GameSessionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class GameSessionServiceImpl implements GameSessionService {

    private final GameSessionRedisRepository gameSessionRedisRepository;

    private final GameSessionMapper gameSessionMapper;

    public List<GameSessionDto> getAllGameSessions() {
        log.debug("ActionLog.getAllGameSessions.start");

        var gameSessionEntities = gameSessionRedisRepository.findAll();
        var gameSessionsDtoList = IterableUtils.toList(gameSessionEntities).stream()
                .map(gameSessionMapper::entityToDto)
                .toList();

        log.debug("ActionLog.getAllGameSessions.end");
        return gameSessionsDtoList;
    }
}