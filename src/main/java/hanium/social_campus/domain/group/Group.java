package hanium.social_campus.domain.group;

import hanium.social_campus.domain.BaseEntity;
import hanium.social_campus.domain.User;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Group extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "group_id")
    private Long id;

    private String groupName;

    @Enumerated(EnumType.STRING)
    private GroupType groupType;

//    @ManyToOne
//    @JoinColumn(name = "groupCategory_id")
//    private GroupCategory groupCategory;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<Join> joins = new ArrayList<>();

}
