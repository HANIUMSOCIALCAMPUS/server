package hanium.social_campus.domain.market;

import hanium.social_campus.domain.BaseEntity;
import hanium.social_campus.domain.Member;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    List<PostImage> postImages = new ArrayList<>();

    public void addPostImage(List<PostImage> postImages) {
        for (PostImage postImage : postImages) {
            this.postImages.add(postImage);
            postImage.setPost(this);
        }
    }
    public static Post create(Member member, String title, DealType dealType, String description, int price, List<PostImage> postImages) {
        Post post = new Post();
        post.member = member;
        post.title = title;
        post.dealType = dealType;
        post.description = description;
        post.price = price;
        post.status = Status.ING;
        post.addPostImage(postImages);
        return post;
    }

    public void edit(String title, String description, int price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }

}
