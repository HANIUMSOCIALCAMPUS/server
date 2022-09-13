package hanium.social_campus.controller.dto.memberDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberInfoDto {

    private final String nickName;
    private final String sex;
    private final String dept;
    private final Integer sno;

}
