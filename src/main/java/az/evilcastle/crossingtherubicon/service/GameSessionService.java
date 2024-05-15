package az.evilcastle.crossingtherubicon.service;

import az.evilcastle.crossingtherubicon.model.dto.GameSessionDto;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class GameSessionService {

    public List<GameSessionDto> getAllGameSessions() {
        log.debug("ActionLog.getAllGameSessions.start");

        log.debug("ActionLog.getAllGameSessions.end");
        return null;
    }
}
