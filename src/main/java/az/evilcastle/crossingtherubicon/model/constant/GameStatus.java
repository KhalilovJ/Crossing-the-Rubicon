package az.evilcastle.crossingtherubicon.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameStatus {

    WAITING(0),
    READY(1),
    STARTED(2);

    private int statusCode;
}
