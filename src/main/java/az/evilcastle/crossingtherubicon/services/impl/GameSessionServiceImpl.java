package az.evilcastle.crossingtherubicon.services.impl;

import az.evilcastle.crossingtherubicon.repository.GameSessionRedisRepo;
import az.evilcastle.crossingtherubicon.services.GameSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameSessionServiceImpl implements GameSessionService {

    private final GameSessionRedisRepo gameSessionRedisRepo;
}
