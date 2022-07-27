package hanium.social_campus.domain.group;

import hanium.social_campus.domain.BaseEntity;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Club extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "club_id")
    private Long id;

    private String clubName;

    @Enumerated(EnumType.STRING)
    private ClubType clubType;


    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Participation> participations = new ArrayList<>();

}
