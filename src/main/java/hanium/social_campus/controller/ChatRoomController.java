package hanium.social_campus.controller;

import hanium.social_campus.chat.RedisSubscriber;
import hanium.social_campus.controller.dto.chatDto.ChatRoomDto;
import hanium.social_campus.controller.exception.ErrorCode;
import hanium.social_campus.controller.exception.SocialException;
import hanium.social_campus.domain.chat.Chat;
import hanium.social_campus.domain.group.Club;
import hanium.social_campus.repository.ChatRepository;
import hanium.social_campus.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user/club/{club_id}")
public class ChatRoomController {

    private final ClubRepository clubRepository;
    private final ChatRepository chatRepository;
    private final RedisSubscriber redisSubscriber;
    private final RedisMessageListenerContainer redisMessageListenerContainer;

    @GetMapping("/chat")
    public ResponseEntity enterChatRoom(@PathVariable(name = "club_id") Long clubId, int offset){
        Club club = clubRepository.findById(clubId).orElseThrow(
                () -> new SocialException(ErrorCode.NOT_FOUND_CLUB)
        );

        ChannelTopic channelTopic = new ChannelTopic("/sub/chat/" + club.getChatRoom().getId());
        redisMessageListenerContainer.addMessageListener(redisSubscriber, channelTopic);

        List<Chat> chats = chatRepository.findChatsByCreatedDateDesc(club.getChatRoom().getId(), offset);
        chats.sort(Comparator.comparing(Chat::getCreateAt));

        return ResponseEntity.ok(new ChatRoomDto(club.getChatRoom().getId(), chats));
    }
}
