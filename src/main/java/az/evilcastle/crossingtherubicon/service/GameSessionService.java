package az.evilcastle.crossingtherubicon.service;

import az.evilcastle.crossingtherubicon.logger.Loggable;
import az.evilcastle.crossingtherubicon.dao.repository.GameSessionRedisRepository;
import az.evilcastle.crossingtherubicon.mapper.GameSessionMapper;
import az.evilcastle.crossingtherubicon.model.dto.GameSessionDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@Loggable
@RequiredArgsConstructor
public class GameSessionService {

    private final GameSessionRedisRepository gameSessionRedisRepository;

    private final GameSessionMapper gameSessionMapper;

    public List<GameSessionDto> getAllGameSessions() {
        var gameSessionEntities = gameSessionRedisRepository.findAll();

        return StreamSupport.stream(gameSessionEntities.spliterator(), false)
                .map(gameSessionMapper::entityToDto)
                .toList();
    }
}