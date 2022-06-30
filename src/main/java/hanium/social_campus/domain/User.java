package hanium.social_campus.domain;

import hanium.social_campus.domain.BaseEntity;
import hanium.social_campus.domain.group.Join;
import hanium.social_campus.domain.market.Post;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String loginId;

    private String password;

    private String nickName;

    private String email;

    private String sex;

    private String university;

    private String dept;

    private Integer sno;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Join> joins = new ArrayList<>();

}
