package hanium.social_campus.controller.dto.memberDto;

import hanium.social_campus.domain.Member;
import lombok.Data;

@Data
public class MemberInfoDto {

    private final Long memberId;
    private final String nickname;
    private final String sex;
    private final String dept;
    private final Integer sno;

    public MemberInfoDto(Member member){
        this.memberId = member.getId();
        this.nickname = member.getNickname();
        this.sex = member.getSex();
        this.dept = member.getDept();
        this.sno = member.getSno();
    }

}
