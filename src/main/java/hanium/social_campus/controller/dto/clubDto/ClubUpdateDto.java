package hanium.social_campus.controller.dto.clubDto;

import hanium.social_campus.domain.group.ClubType;
import lombok.Data;

@Data
public class ClubUpdateDto {

    private final String clubName;
    private final Integer maximumCount;
    private final ClubType clubType;

}
