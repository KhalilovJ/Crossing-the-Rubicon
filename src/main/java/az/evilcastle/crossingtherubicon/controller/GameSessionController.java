package az.evilcastle.crossingtherubicon.controller;

import az.evilcastle.crossingtherubicon.model.dto.PlayerDto;
import az.evilcastle.crossingtherubicon.model.dto.gamesession.ConnectToLobbyDto;
import az.evilcastle.crossingtherubicon.model.dto.gamesession.CreateGameSessionDto;
import az.evilcastle.crossingtherubicon.model.dto.gamesession.GameSessionDto;
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
    public List<GameSessionDto> getAllGameSessions() {
        return service.getAllGameSessions();
    }


    @PostMapping
    public GameSessionDto createSession(@RequestBody CreateGameSessionDto dto,PlayerDto test){
       return service.createGameSession(dto,test);
    }

    @PostMapping("/connect")
    public GameSessionDto connectToLobby(@RequestBody ConnectToLobbyDto dto,PlayerDto test){
        return service.connectGameSession(test,dto);
    }
}
