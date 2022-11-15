package hanium.social_campus.repository.market;

import hanium.social_campus.domain.market.DealType;
import hanium.social_campus.domain.market.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // paging 여부에 따라 변경 가능
    @EntityGraph(attributePaths = {"member"})
    List<Post> findByDealType(DealType dealType);

    // 상세 조회
    @EntityGraph(attributePaths = {"member", "postImages"})
    @Query("select p from Post p where p.id = :id")
    Optional<Post> findByIdDetail(@Param("id") Long id);

}
