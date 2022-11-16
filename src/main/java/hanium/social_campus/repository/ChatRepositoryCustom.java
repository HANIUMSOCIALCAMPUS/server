package hanium.social_campus.repository;

import hanium.social_campus.domain.chat.Chat;

import java.util.List;

public interface ChatRepositoryCustom {

    List<Chat> findChatsByCreatedDateDesc(Long chatRoomId, int offset);
}
