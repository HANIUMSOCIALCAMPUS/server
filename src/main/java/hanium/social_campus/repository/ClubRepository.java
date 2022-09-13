package hanium.social_campus.repository;

import hanium.social_campus.domain.group.Club;
import hanium.social_campus.domain.group.ClubType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {

    List<Club> findByClubType(ClubType clubType);

}
