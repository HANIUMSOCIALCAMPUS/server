package hanium.social_campus.repository;

import hanium.social_campus.domain.chat.Chat;
import hanium.social_campus.domain.chat.ChatRoom;
import hanium.social_campus.domain.market.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> , ChatRepositoryCustom {

    List<Chat> findByChatRoom(ChatRoom chatRoom, Pageable pageable);
}
