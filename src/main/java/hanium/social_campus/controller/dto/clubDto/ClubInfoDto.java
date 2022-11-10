package hanium.social_campus.controller.dto.clubDto;

import hanium.social_campus.controller.dto.memberDto.MemberInfoDto;
import hanium.social_campus.domain.group.Club;
import hanium.social_campus.domain.group.ClubType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ClubInfoDto {

    private final Long clubId;
    private final String clubName;
    private final Integer currentCount;
    private final Integer maximumCount;
    private final ClubType clubType;
    private final Long chatRoomId;
    private final String owner;
    private final List<MemberInfoDto> memberInfoDtos;

    public ClubInfoDto(Club club){
        this.clubId = club.getId();
        this.clubName = club.getClubName();
        this.currentCount = club.getParticipations().size();
        this.maximumCount = club.getMaximumCount();
        this.clubType = club.getClubType();
        this.chatRoomId = club.getChatRoom().getId();
        this.owner = club.getParticipations().get(0).getMember().getNickname();
        this.memberInfoDtos = club.getParticipations().stream().map(p -> new MemberInfoDto(p.getMember())).collect(Collectors.toList());
    }

    public ClubInfoDto(Club club, List<MemberInfoDto> memberInfoDtos){
        this.clubId = club.getId();
        this.clubName = club.getClubName();
        this.currentCount = club.getParticipations().size();
        this.maximumCount = club.getMaximumCount();
        this.clubType = club.getClubType();
        this.chatRoomId = club.getChatRoom().getId();
        this.owner = club.getParticipations().get(0).getMember().getNickname();
        this.memberInfoDtos = memberInfoDtos;
    }

}
