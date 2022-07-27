package hanium.social_campus.domain.group;

import hanium.social_campus.domain.BaseEntity;
import hanium.social_campus.domain.Member;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Participation extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "participation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @Builder
    public Participation(Member member, Club club){
        this.member = member;
        this.club = club;
    }


}
