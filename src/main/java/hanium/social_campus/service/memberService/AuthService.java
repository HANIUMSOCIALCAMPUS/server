package hanium.social_campus.service.memberService;

import hanium.social_campus.auth.jwt.TokenDto;
import hanium.social_campus.controller.dto.memberDto.JoinDto;
import hanium.social_campus.domain.Authority;
import hanium.social_campus.domain.Member;
import hanium.social_campus.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    public void signUp(JoinDto joinDto) {
        Member member = Member.builder()
                .loginId(joinDto.getLoginId())
                .password(joinDto.getPassword())
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

    //public TokenDto signIn()

}
