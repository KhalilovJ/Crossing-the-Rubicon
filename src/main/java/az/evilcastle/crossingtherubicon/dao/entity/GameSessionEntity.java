package az.evilcastle.crossingtherubicon.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class GameSessionEntity implements Serializable {
    @Id
    private String id;
    private String sessionName;
}
