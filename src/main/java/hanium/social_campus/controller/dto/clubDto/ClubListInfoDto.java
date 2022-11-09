package hanium.social_campus.controller.dto.clubDto;

import hanium.social_campus.domain.group.Club;
import hanium.social_campus.domain.group.ClubType;
import lombok.Data;

@Data
public class ClubListInfoDto {

    private Long clubId;
    private String clubName;
    private Integer currentCount;
    private Integer maximumCount;
    private ClubType clubType;

    public ClubListInfoDto(Club club){
        this.clubId = club.getId();
        this.clubName = club.getClubName();
        this.currentCount = club.getParticipations().size();
        this.maximumCount = club.getMaximumCount();
        this.clubType = club.getClubType();
    }

}
