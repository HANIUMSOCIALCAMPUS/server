package hanium.social_campus.controller.dto.clubDto;

import hanium.social_campus.controller.dto.memberDto.MemberInfoDto;
import hanium.social_campus.domain.group.ClubType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ClubInfoDto {

    private final String clubName;
    private final Integer maximumCount;
    private final ClubType clubType;

    private final List<MemberInfoDto> memberInfoDtos;
}
