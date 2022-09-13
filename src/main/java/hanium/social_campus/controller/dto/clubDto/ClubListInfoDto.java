package hanium.social_campus.controller.dto.clubDto;

import hanium.social_campus.domain.group.ClubType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClubListInfoDto {

    private String clubName;
    private Integer currentCount;
    private Integer maximumCount;
    private ClubType clubType;

}
