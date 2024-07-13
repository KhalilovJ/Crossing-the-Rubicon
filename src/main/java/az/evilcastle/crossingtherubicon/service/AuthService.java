package az.evilcastle.crossingtherubicon.service;
import az.evilcastle.crossingtherubicon.dao.entity.player.PlayerEntity;
import az.evilcastle.crossingtherubicon.dao.entity.player.Token;
import az.evilcastle.crossingtherubicon.dao.entity.player.TokenType;
import az.evilcastle.crossingtherubicon.dao.repository.PlayerRepository;
import az.evilcastle.crossingtherubicon.dao.repository.TokenRepository;
import az.evilcastle.crossingtherubicon.model.dto.auth.AuthRequestDto;
import az.evilcastle.crossingtherubicon.model.dto.auth.AuthResponseDto;
import az.evilcastle.crossingtherubicon.model.dto.auth.RegisterRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;



    public AuthResponseDto register(RegisterRequestDto dto){

        PlayerEntity player = PlayerEntity.builder()
                .email(dto.email())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .number(dto.number())
                .active(true).build();

        PlayerEntity savedPlayer =playerRepository.save(player);

        var jwtToken = jwtService.generateToken(player);
        var refreshToken = jwtService.generateRefreshToken(player);
        savePlayerToken(savedPlayer,jwtToken);

        return new AuthResponseDto(jwtToken,refreshToken);
    }


    public AuthResponseDto authenticate(AuthRequestDto request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        PlayerEntity player =playerRepository.findPlayerEntityByEmailAndActiveIsTrue(request.email())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(player);
        var refreshToken = jwtService.generateRefreshToken(player);
        revokeAllUserTokens(player);
        savePlayerToken(player,jwtToken);

        return new AuthResponseDto(jwtToken,refreshToken);

    }


    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.playerRepository.findPlayerEntityByEmailAndActiveIsTrue(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                savePlayerToken(user, accessToken);
                var authResponse = new AuthResponseDto(accessToken,refreshToken);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }




    private void revokeAllUserTokens(PlayerEntity player) {
        var validUserTokens = tokenRepository.findAllByExpiredIsFalseAndRevokedIsFalseAndPlayer(player);
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            log.info("Token revoked:"+ token.getPlayer().getEmail());
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }



    private void savePlayerToken(PlayerEntity player, String jwtToken) {
        var token = Token.builder()
                .player(player)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}
