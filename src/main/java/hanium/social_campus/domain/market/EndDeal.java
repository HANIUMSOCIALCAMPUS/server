package hanium.social_campus.domain.market;

import hanium.social_campus.domain.BaseEntity;
import hanium.social_campus.domain.Member;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class EndDeal extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "endDeal_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="seller_id")
    private Member seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private Member buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

}
