package hanium.social_campus.controller;

import hanium.social_campus.auth.jwt.AccessTokenDto;
import hanium.social_campus.auth.jwt.TokenDto;
import hanium.social_campus.controller.dto.memberDto.JoinDto;
import hanium.social_campus.controller.dto.memberDto.LoginDto;
import hanium.social_campus.controller.dto.memberDto.LoginInfoDto;
import hanium.social_campus.controller.exception.ErrorCode;
import hanium.social_campus.controller.exception.SocialException;
import hanium.social_campus.domain.Member;
import hanium.social_campus.domain.email.VerifyCode;
import hanium.social_campus.repository.MemberRepository;
import hanium.social_campus.service.email.VerifyCodeService;
import hanium.social_campus.service.memberService.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final VerifyCodeService verifyCodeService;

    @PostMapping("/join")
    public ResponseEntity join(@RequestBody @Validated JoinDto joinDto) {
        authService.signUp(joinDto);
        log.info("회원가입 성공");

        return ResponseEntity.ok(true);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated LoginDto loginDto, HttpServletResponse response) {

        TokenDto tokenDto = authService.signIn(loginDto);

        response.setHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.setHeader("Set-Cookie", setRefreshToken(tokenDto.getRefreshToken()).toString());

        Member member = memberRepository.findByLoginId(loginDto.getLoginId()).orElseThrow(
                () -> new SocialException(ErrorCode.NOT_FOUND_MEMBER)
        );

        return ResponseEntity.ok(new LoginInfoDto(member.getId(), member.getNickname()));
    }

    //아이디 중복검사
    @PostMapping("/duplicate-loginId")
    public ResponseEntity duplicateId(@RequestBody Map<String, String> loginIdMap) {
        log.info(loginIdMap.get("loginId"));
        if (memberRepository.findByLoginId(loginIdMap.get("loginId")).isPresent()) {
            return ResponseEntity.ok(false);
        } else {
            return ResponseEntity.ok(true);
        }
    }


    //닉네임 중복 검사
    @PostMapping("/duplicate-nickname")
    public ResponseEntity duplicateNickname(@RequestBody Map<String, String> nicknameMap) {
        log.info(nicknameMap.get("nickname"));
        if (memberRepository.findByNickname(nicknameMap.get("nickname")).isPresent()) {
            return ResponseEntity.ok(false);
        } else {
            return ResponseEntity.ok(true);
        }
    }

    @PostMapping("/reissue")
    public void refresh(@CookieValue(name = "RefreshToken") String refreshToken, HttpServletResponse response) {
        TokenDto tokenDto = authService.refresh(refreshToken);

        response.setHeader("Set-Cookie", setRefreshToken(tokenDto.getRefreshToken()).toString());
        response.setHeader("Authorization", "Bearer "+tokenDto.getAccessToken());
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


    //메일로 인증코드 보내기
    @PostMapping("/mail-auth")
    public ResponseEntity mailAuthReq(@RequestBody Map<String, String> emailMap) {
        log.info(emailMap.get("email"));
        verifyCodeService.createVerifyCode(emailMap.get("email"));
        return ResponseEntity.ok(true);
    }


    //메일 인증 결과
    @PostMapping("/mailcode-auth")
    public ResponseEntity mailCodeAuth(@RequestBody Map<String, String> codeMap) {
        log.info(codeMap.get("code"));
        Optional<VerifyCode> result = authService.confirmEmail(codeMap.get("code"));
        if (result.isEmpty()) {
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);
    }
}
