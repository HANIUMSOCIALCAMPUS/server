package hanium.social_campus.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import hanium.social_campus.controller.dto.chatDto.ChatMessageResponseDto;
import hanium.social_campus.controller.exception.ErrorCode;
import hanium.social_campus.controller.exception.SocialException;
import hanium.social_campus.domain.chat.Chat;
import hanium.social_campus.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRepository chatRepository;

    /**
     * Redis에서 메시지가 발행(publish)되면 대기하고 있던 onMessage가 해당 메시지를 받아 처리한다.
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
            ChatMessageResponseDto roomMessage = objectMapper.readValue(publishMessage, ChatMessageResponseDto.class);

            Chat chat = chatRepository.findById(roomMessage.getChatId()).orElseThrow(
                    () -> new SocialException(ErrorCode.NOT_FOUND_CHATROOM)
            );
            // 브로드캐스팅
            messagingTemplate.convertAndSend("/sub/chat/" + chat.getChatRoom().getId(), roomMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
