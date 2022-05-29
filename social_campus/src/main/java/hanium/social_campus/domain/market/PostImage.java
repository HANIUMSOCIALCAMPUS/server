package hanium.social_campus.domain.market;

import hanium.social_campus.domain.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class PostImage extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "postImage_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String url;
}
