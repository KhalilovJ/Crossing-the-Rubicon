package az.evilcastle.crossingtherubicon.controller;

import az.evilcastle.crossingtherubicon.model.dto.gamesession.LobbyDto;
import az.evilcastle.crossingtherubicon.service.GameSessionService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/game-sessions")
public class GameSessionController {

    private final GameSessionService service;

    @GetMapping
    public List<LobbyDto> getAllGameSessions() {
        return service.getAllGameSessions();
    }

}
