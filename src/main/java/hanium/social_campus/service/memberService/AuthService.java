package hanium.social_campus.service.memberService;

import hanium.social_campus.auth.jwt.TokenDto;
import hanium.social_campus.auth.jwt.TokenProvider;
import hanium.social_campus.controller.dto.memberDto.JoinDto;
import hanium.social_campus.controller.dto.memberDto.LoginDto;
import hanium.social_campus.domain.Authority;
import hanium.social_campus.domain.Member;
import hanium.social_campus.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;

    public void signUp(JoinDto joinDto) {
        Member member = Member.builder()
                .loginId(joinDto.getLoginId())
                .password(passwordEncoder.encode(joinDto.getPassword()))
                .email(joinDto.getEmail())
                .sex(joinDto.getSex())
                .nickName(joinDto.getNickName())
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

}
