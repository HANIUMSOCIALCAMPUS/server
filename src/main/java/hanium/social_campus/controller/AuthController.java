package hanium.social_campus.controller;

import hanium.social_campus.auth.jwt.AccessTokenDto;
import hanium.social_campus.auth.jwt.TokenDto;
import hanium.social_campus.auth.jwt.TokenProvider;
import hanium.social_campus.controller.dto.memberDto.JoinDto;
import hanium.social_campus.controller.dto.memberDto.LoginDto;
import hanium.social_campus.service.memberService.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public void join(@RequestBody @Validated JoinDto joinDto) {
        authService.signUp(joinDto);
    }

    @PostMapping("/sign-in")
    public ResponseEntity signIn(@RequestBody @Validated LoginDto loginDto, HttpServletResponse response) {

        TokenDto tokenDto = authService.signIn(loginDto);

        response.setHeader("Set-Cookie", setRefreshToken(tokenDto.getRefreshToken()).toString());

        return ResponseEntity.ok(new AccessTokenDto(tokenDto.getAccessToken()));

    }

    @PostMapping("/reissue")
    public ResponseEntity refresh(@CookieValue(name = "RefreshToken") String refreshToken, HttpServletResponse response) {
        TokenDto tokenDto = authService.refresh(refreshToken);

        response.setHeader("Set-Cookie", setRefreshToken(tokenDto.getRefreshToken()).toString());

        return ResponseEntity.ok(new AccessTokenDto(tokenDto.getAccessToken()));
    }

    public ResponseCookie setRefreshToken(String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from("RefreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .maxAge(60 * 60 * 24 * 7)  //7일
                .sameSite("None")
                .path("/")
                .build();
        return cookie;
    }
}
