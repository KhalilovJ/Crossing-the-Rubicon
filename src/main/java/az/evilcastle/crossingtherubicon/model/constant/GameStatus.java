package az.evilcastle.crossingtherubicon.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameStatus {

    WAITING(0),
    STARTED(1);

    private int statusCode;
}
