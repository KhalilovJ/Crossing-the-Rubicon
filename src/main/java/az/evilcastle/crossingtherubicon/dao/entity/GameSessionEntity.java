package az.evilcastle.crossingtherubicon.dao.entity;

import az.evilcastle.crossingtherubicon.model.constant.GameStatus;
import az.evilcastle.crossingtherubicon.model.dto.PlayerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.List;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class GameSessionEntity implements Serializable {
    @Id
    private String id;
    private String lobbyName;
    private String password;
    private List<PlayerDto> players;
    private GameStatus status;

}
