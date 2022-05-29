package hanium.social_campus.domain.market;

import hanium.social_campus.domain.BaseEntity;
import hanium.social_campus.domain.User;
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
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private DealType dealType;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Integer likes;

}
