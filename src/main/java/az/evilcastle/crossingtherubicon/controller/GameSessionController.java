package az.evilcastle.crossingtherubicon.controller;

import az.evilcastle.crossingtherubicon.model.dto.gamesession.LobbyDto;
import az.evilcastle.crossingtherubicon.service.GameSessionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/game-sessions")
@Slf4j
public class GameSessionController {

    private final GameSessionService service;

    @GetMapping
    public List<LobbyDto> getAllGameSessions() throws Exception{
        try {
            return service.getAllGameSessions();
        } catch (Exception e){
            log.info("Error occured during connection");
            throw e;
        }

    }

}
