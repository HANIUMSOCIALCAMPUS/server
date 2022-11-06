package hanium.social_campus.service.memberService;

import hanium.social_campus.auth.jwt.TokenDto;
import hanium.social_campus.auth.jwt.TokenProvider;
import hanium.social_campus.controller.dto.memberDto.JoinDto;
import hanium.social_campus.controller.dto.memberDto.LoginDto;
import hanium.social_campus.domain.Authority;
import hanium.social_campus.domain.Member;
import hanium.social_campus.domain.email.VerifyCode;
import hanium.social_campus.repository.MemberRepository;
import hanium.social_campus.service.email.VerifyCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final VerifyCodeService verifyCodeService;

    public void signUp(JoinDto joinDto) {
        Member member = Member.builder()
                .loginId(joinDto.getLoginId())
                .password(passwordEncoder.encode(joinDto.getPassword()))
                .email(joinDto.getEmail())
                .sex(joinDto.getSex())
                .nickname(joinDto.getNickname())
                .university(joinDto.getUniversity())
                .dept(joinDto.getDept())
                .sno(joinDto.getSno())
                .authority(Authority.ROLE_USER)
                .build();

        memberRepository.join(member);
    }

    public TokenDto signIn(LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getLoginId(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        return tokenDto;
    }

    public TokenDto refresh(String refreshToken) {
        Authentication authentication = tokenProvider.getAuthentication(refreshToken);

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        return tokenDto;
    }

    /**
     * 이메일 인증 코드 확인
     * @param code
     * @return
     */
    @Transactional
    public Optional<VerifyCode> confirmEmail(String code) {
        Optional<VerifyCode> findCode = verifyCodeService.findExpiredCode(code);
        if (findCode.isPresent()) {
            findCode.get().useCode();
        }
        return findCode;
    }

}
