package hanium.social_campus.controller;

import hanium.social_campus.auth.jwt.TokenDto;
import hanium.social_campus.controller.dto.memberDto.JoinDto;
import hanium.social_campus.controller.dto.memberDto.LoginDto;
import hanium.social_campus.service.memberService.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public void join(@RequestBody @Valid JoinDto joinDto) {
        authService.signUp(joinDto);
    }

    @PostMapping("/sign-in")
    public void signIn(@RequestBody @Valid LoginDto loginDto) {
    }
}
