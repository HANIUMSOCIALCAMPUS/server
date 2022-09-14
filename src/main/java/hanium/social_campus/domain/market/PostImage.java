package hanium.social_campus.domain.market;

import hanium.social_campus.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "postImage_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String url;

    public void setPost(Post post) {
        this.post = post;
    }

    public PostImage(String url) {
        this.url = url;
    }
}
