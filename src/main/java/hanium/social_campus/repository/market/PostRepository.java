package hanium.social_campus.repository.market;

import hanium.social_campus.domain.market.DealType;
import hanium.social_campus.domain.market.Post;
import hanium.social_campus.domain.market.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    // paging 여부에 따라 변경 가능
    @EntityGraph(attributePaths = {"member"})
    @Query("select p from Post p where p.dealType = :dealType order by p.createAt desc")
    List<Post> findByDealType(DealType dealType);

    @Override
    @EntityGraph(attributePaths = {"member"})
    Optional<Post> findById(Long id);

    // 상세 조회
    @EntityGraph(attributePaths = {"member", "postImages", "chatRoom"})
    @Query("select p from Post p where p.id = :id")
    Optional<Post> findByIdDetail(@Param("id") Long id);

    @EntityGraph(attributePaths = {"member"})
    @Query("select p from Post p where p.title like %:title%")
    List<Post> findByTitleSearch(@Param("title") String title);

}
