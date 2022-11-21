package hanium.social_campus.domain.market;

import hanium.social_campus.domain.BaseEntity;
import hanium.social_campus.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Note extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "note_id")
    private Long id;

    @JoinColumn(name = "sender_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member sender;

    @JoinColumn(name = "receiver_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member receiver;

    @JoinColumn(name = "post_id")
    @ManyToOne
    private Post post;

    private String message;

    public void addNote(Post post) {
        this.post = post;
        post.getNotes().add(this);
    }

    @Builder
    public Note(Post post, Member member, String message) {
        this.addNote(post);
        this.sender = post.getMember();
        this.receiver = member;
        this.message = message;
    }

}
