package hanium.social_campus.domain.group;

import hanium.social_campus.domain.BaseEntity;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class GroupCategory extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "GroupCategory_id")
    private Long id;

    private String categoryName;

    @OneToMany(mappedBy = "groupCategory")
    private List<Group> groups = new ArrayList<>();


}
