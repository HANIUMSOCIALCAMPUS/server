package hanium.social_campus.controller.dto.memberDto;

import hanium.social_campus.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MyDto {

    private String nickname;

    private String email;

    private String university;

    private String dept;

    private int sno;

    public MyDto(Member member) {
        nickname = member.getNickname();
        email = member.getEmail();
        university = member.getUniversity();
        dept = member.getDept();
        sno = member.getSno();
    }
}
