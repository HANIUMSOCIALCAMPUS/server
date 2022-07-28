package hanium.social_campus.domain.group;

import hanium.social_campus.controller.dto.clubDto.ClubCreateDto;
import hanium.social_campus.domain.BaseEntity;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Club extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "club_id")
    private Long id;

    private String clubName;

    private Integer maximumCount;

    @Enumerated(EnumType.STRING)
    private ClubType clubType;

    // 그룹 삭제 시 참가도 삭제
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Participation> participations = new ArrayList<>();


    //==생성 메서드==//
    public static Club create(ClubCreateDto clubCreateDto) {
        Club club = new Club();
        club.updateClub(clubCreateDto.getClubName(), clubCreateDto.getMaximumCount(), clubCreateDto.getClubType());

        return club;
    }


    public void updateClub(String clubName, int maximumCount, ClubType clubType){
        this.clubName = clubName;
        this.maximumCount = maximumCount;
        this.clubType = clubType;
    }

}
