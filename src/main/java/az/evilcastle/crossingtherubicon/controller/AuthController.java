package az.evilcastle.crossingtherubicon.controller;

import az.evilcastle.crossingtherubicon.model.dto.auth.AuthRequestDto;
import az.evilcastle.crossingtherubicon.model.dto.auth.AuthResponseDto;
import az.evilcastle.crossingtherubicon.model.dto.auth.RegisterRequestDto;
import az.evilcastle.crossingtherubicon.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;



    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponseDto> signUp(@RequestBody RegisterRequestDto request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request){
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authService.refreshToken(request, response);
    }

}
