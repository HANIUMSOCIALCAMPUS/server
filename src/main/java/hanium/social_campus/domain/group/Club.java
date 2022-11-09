package hanium.social_campus.domain.group;

import hanium.social_campus.controller.dto.clubDto.ClubCreateDto;
import hanium.social_campus.controller.dto.clubDto.ClubListInfoDto;
import hanium.social_campus.domain.BaseEntity;
import hanium.social_campus.domain.chat.ChatRoom;
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

    @OneToMany(mappedBy = "club")
    private List<Participation> participations = new ArrayList<>();

    @OneToOne(mappedBy = "club", fetch = FetchType.LAZY)
    private ChatRoom chatRoom;


    //==생성 메서드==//
    public static Club create(ClubCreateDto clubCreateDto, ChatRoom chatRoom) {
        Club club = new Club();
        club.initialChatRoom(chatRoom);
        club.updateClub(clubCreateDto.getClubName(), clubCreateDto.getMaximumCount(), clubCreateDto.getClubType());

        return club;
    }


    public void updateClub(String clubName, int maximumCount, ClubType clubType){
        this.clubName = clubName;
        this.maximumCount = maximumCount;
        this.clubType = clubType;
    }

    public void initialChatRoom(ChatRoom chatRoom){
        this.chatRoom = chatRoom;
        chatRoom.insertClub(this);
    }

    public ClubListInfoDto toClubListInfoDto(){
        return new ClubListInfoDto(this);
    }

}
