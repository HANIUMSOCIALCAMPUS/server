package hanium.social_campus.repository;

import hanium.social_campus.domain.chat.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepositoryCustom{

    private final EntityManager em;

    @Override
    public List<Chat> findChatsByCreatedDateDesc(Long chatRoomId, int offset){
        String query = "select c from Chat c join fetch c.chatRoom cr where cr.id = :chatRoomId order by c.createAt desc";
        return em.createQuery(query, Chat.class)
                .setParameter("chatRoomId", chatRoomId)
                .setFirstResult(offset)
                .setMaxResults(50)
                .getResultList();

    }
}
