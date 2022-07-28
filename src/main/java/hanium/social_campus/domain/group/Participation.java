package hanium.social_campus.domain.group;

import hanium.social_campus.domain.BaseEntity;
import hanium.social_campus.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Participation extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "participation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "club_id")
    private Club club;


    //==생성 메서드==//
    public static Participation create(Member member, Club club) {
        Participation participation = new Participation();

        participation.updateParticipation(member, club);
        member.getParticipations().add(participation);
        club.getParticipations().add(participation);

        return participation;
    }

    public void updateParticipation(Member member, Club club){
        this.member = member;
        this.club = club;
    }

}
