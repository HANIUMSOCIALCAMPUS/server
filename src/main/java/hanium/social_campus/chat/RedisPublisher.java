package hanium.social_campus.chat;

import hanium.social_campus.controller.dto.chatDto.ChatMessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, ChatMessageResponseDto message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
