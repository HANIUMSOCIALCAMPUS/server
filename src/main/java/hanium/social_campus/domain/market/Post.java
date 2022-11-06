package hanium.social_campus.domain.market;

import hanium.social_campus.domain.BaseEntity;
import hanium.social_campus.domain.Member;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private DealType dealType;

    @Enumerated(EnumType.STRING)
    private Status status;

    private int price;

}
