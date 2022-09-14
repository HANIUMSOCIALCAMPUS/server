package hanium.social_campus.repository.market;

import hanium.social_campus.domain.market.DealType;
import hanium.social_campus.domain.market.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = {"member", "postImages"})
    List<Post> findByDealType(DealType dealType);
}
