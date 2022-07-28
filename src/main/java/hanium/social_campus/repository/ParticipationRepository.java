package hanium.social_campus.repository;

import hanium.social_campus.domain.group.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    List<Participation> findByClubId(Long clubId);

    Boolean existsByClubIdAndMemberId(Long clubId, Long memberId);

    // 방장 찾기
    @Query(value = "select member_id from participation where club_id = :clubId limit 1", nativeQuery = true)
    Long findMemberIdByClubId(@Param(value = "clubId")Long clubId);

}
