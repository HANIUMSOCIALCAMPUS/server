package hanium.social_campus.repository;

import hanium.social_campus.domain.group.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
}
