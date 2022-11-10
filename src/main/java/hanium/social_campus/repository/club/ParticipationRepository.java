package hanium.social_campus.repository.club;

import hanium.social_campus.domain.Member;
import hanium.social_campus.domain.group.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    List<Participation> findByClubId(Long clubId);
    List<Participation> findByMember(Member member);

    Optional<Participation> findByClubIdAndMemberId(Long clubId, Long memberId);

    // 방장 찾기
    @Query(value = "select member_id from participation where club_id = :clubId limit 1", nativeQuery = true)
    Long findMemberIdByClubId(@Param(value = "clubId")Long clubId);

}
