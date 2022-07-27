package hanium.social_campus.controller;

import hanium.social_campus.auth.config.SecurityUtil;
import hanium.social_campus.controller.dto.memberDto.MyDto;
import hanium.social_campus.domain.Member;
import hanium.social_campus.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/mypage")
    public ResponseEntity mypage() {
        Member member = memberRepository.findByLoginId(SecurityUtil.getCurrentMemberId()).orElseThrow(
                () -> new EntityNotFoundException("존재하지 않는 회원입니다.")
        );
        return ResponseEntity.ok(new MyDto(member.getNickName(), member.getEmail()));
    }

    @PostMapping("/log-out")
    public void logOut(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("RefreshToken", null)
                .maxAge(0)
                .path("/")
                .sameSite("None")  //빌드 후 배포 시엔 수정 예정
                .secure(true)
                .httpOnly(true)
                .build();
        response.setHeader("Set-Cookie", cookie.toString());
    }



}
