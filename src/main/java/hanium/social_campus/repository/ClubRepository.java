package hanium.social_campus.repository;

import hanium.social_campus.domain.group.Club;
import hanium.social_campus.domain.group.ClubType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Repository
public class ClubRepository {

    private final EntityManager em;

    public void save(Club club){
        em.persist(club);
    }

    public Optional<Club> findById(Long id){
        return Optional.ofNullable(em.find(Club.class, id));
    }

    public void delete(Club club){
        em.remove(club);
    }

    public List<Club> findByClubType(ClubType clubType){
        return em.createQuery("select distinct c from Club c where c.clubType = :clubType", Club.class)
                .setParameter("clubType", clubType)
                .getResultList();
    }

    public List<Club> findWithKeyword(String keyword, int offset){
        return em.createQuery("select distinct c from Club c where c.clubName like CONCAT('%', :keyword, '%') order by c.createAt desc")
                .setParameter("keyword", keyword)
                .setFirstResult(offset)
                .setMaxResults(10)
                .getResultList();
    }

}
