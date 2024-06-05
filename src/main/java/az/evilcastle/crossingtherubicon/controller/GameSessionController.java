package az.evilcastle.crossingtherubicon.controller;

import az.evilcastle.crossingtherubicon.model.dto.gamesession.GameSessionDto;
import az.evilcastle.crossingtherubicon.service.GameSessionService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/game-sessions")
public class GameSessionController {

    private final GameSessionService service;

    @GetMapping
    public List<GameSessionDto> getAllGameSessions() {
        return service.getAllGameSessions();
    }
}
