package hanium.social_campus.domain;

import hanium.social_campus.domain.group.Participation;
import hanium.social_campus.domain.market.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String loginId;

    private String password;

    private String nickName;

    private String email;

    private String sex;

    private String university;

    private String dept;

    private Integer sno;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Participation> participations = new ArrayList<>();

    @Builder
    public Member(String loginId, String password, String nickName, String email, String sex
                  ,String university, String dept, Integer sno, Authority authority) {
        this.loginId = loginId;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.sex = sex;
        this.university = university;
        this.dept = dept;
        this.sno = sno;
        this.authority = authority;
    }
}
