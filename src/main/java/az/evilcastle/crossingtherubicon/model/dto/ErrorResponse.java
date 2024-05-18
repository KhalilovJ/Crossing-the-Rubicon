package az.evilcastle.crossingtherubicon.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ErrorResponse(
        String code,
        String message,
        Integer httpCode,
        Map<String, List<String>> validationError
) {
    public ErrorResponse(String code, String message) {
        this(code, message, null, null);
    }

    public ErrorResponse(String code, String message, Map<String, List<String>> validationError) {
        this(code, message, null, validationError);
    }
}
