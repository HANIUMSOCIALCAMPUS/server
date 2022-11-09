package hanium.social_campus.service.chatService;

import hanium.social_campus.controller.dto.chatDto.SendMessageDto;
import hanium.social_campus.controller.exception.ErrorCode;
import hanium.social_campus.controller.exception.SocialException;
import hanium.social_campus.domain.Member;
import hanium.social_campus.domain.chat.Chat;
import hanium.social_campus.domain.chat.ChatRoom;
import hanium.social_campus.repository.ChatRepository;
import hanium.social_campus.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;

    // 채팅 저장
    @Transactional
    public Chat create(Member member, SendMessageDto sendMessageDto) {
        ChatRoom chatRoom = chatRoomRepository.findById(sendMessageDto.getRoomId()).orElseThrow(
                () -> new SocialException(ErrorCode.NOT_FOUND_CHATROOM)
        );

        Chat chat = Chat.create(chatRoom, member.getNickname(), sendMessageDto.getMessage());
        chatRepository.save(chat);
        return chat;
    }

}
