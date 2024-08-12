package az.evilcastle.crossingtherubicon.model.dto.auth;

public record AuthRequestDto(
        String email,
        String password
) {
}
