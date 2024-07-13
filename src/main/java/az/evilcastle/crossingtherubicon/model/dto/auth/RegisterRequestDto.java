package az.evilcastle.crossingtherubicon.model.dto.auth;

public record RegisterRequestDto(
        String firstName,
        String lastName,
        String username,
        String email,
        String password,
        String number
){
}
