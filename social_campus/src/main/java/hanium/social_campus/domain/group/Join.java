package hanium.social_campus.domain.group;

import hanium.social_campus.domain.BaseEntity;
import hanium.social_campus.domain.User;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Join extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "join_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;


}
