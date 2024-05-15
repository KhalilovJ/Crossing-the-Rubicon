package az.evilcastle.crossingtherubicon.models;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@RedisHash("game_session")
public class GameSession implements Serializable {
    @Id
    private String id;
    private String sessionName;
}
