package hanium.social_campus.repository.market;

import hanium.social_campus.domain.Member;
import hanium.social_campus.domain.market.Note;
import hanium.social_campus.domain.market.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    @EntityGraph(attributePaths = {"post", "post.member", "post.postImages"})
    @Query("select n from Note n where n.receiver = :member or n.sender = :member order by n.createAt desc")
    List<Note> findAll(@Param("member") Member member);

    @Query("select n from Note n where (n.receiver = :member or n.sender = :member) and n.post = :post order by n.createAt desc")
    List<Note> findAllWithDetail(@Param("member") Member member, @Param("post") Post post);
}
