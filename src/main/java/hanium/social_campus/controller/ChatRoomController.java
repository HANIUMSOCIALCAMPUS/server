package hanium.social_campus.controller;

import hanium.social_campus.chat.RedisSubscriber;
import hanium.social_campus.controller.dto.chatDto.ChatRoomDto;
import hanium.social_campus.controller.exception.ApiException;
import hanium.social_campus.controller.exception.ErrorCode;
import hanium.social_campus.controller.exception.SocialException;
import hanium.social_campus.domain.chat.Chat;
import hanium.social_campus.domain.group.Club;
import hanium.social_campus.domain.market.Post;
import hanium.social_campus.repository.ChatRepository;
import hanium.social_campus.repository.club.ClubRepository;
import hanium.social_campus.repository.market.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping("/user")
public class ChatRoomController {

    private final ClubRepository clubRepository;
    private final ChatRepository chatRepository;

    private final PostRepository postRepository;
    private final RedisSubscriber redisSubscriber;
    private final RedisMessageListenerContainer redisMessageListenerContainer;

    @GetMapping("/club/{club_id}/chat") // 승윤 전달
    public ResponseEntity enterGroupChatRoom(@PathVariable(name = "club_id") Long clubId, int offset){
        Club club = clubRepository.findById(clubId).orElseThrow(
                () -> new SocialException(ErrorCode.NOT_FOUND_CLUB)
        );

        //
        ChannelTopic channelTopic = new ChannelTopic("/sub/chat/" + club.getChatRoom().getId());
        redisMessageListenerContainer.addMessageListener(redisSubscriber, channelTopic);

        List<Chat> chats = chatRepository.findChatsByCreatedDateDesc(club.getChatRoom().getId(), offset);
        chats.sort(Comparator.comparing(Chat::getCreateAt));

        return ResponseEntity.ok(new ChatRoomDto(club.getChatRoom().getId(), chats));
    }

    @GetMapping("/market/{post_id}/chat")
    public ResponseEntity enterMarketChatRoom(@PathVariable(name = "post_id")Long id
            ,@PageableDefault(size=50, sort="createAt", direction = Sort.Direction.DESC)Pageable pageable){
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ApiException(ErrorCode.NOT_FOUND_MARKET)
        );
        ChannelTopic channelTopic = new ChannelTopic("/sub/chat/" + post.getChatRoom().getId());
        redisMessageListenerContainer.addMessageListener(redisSubscriber, channelTopic);

        List<Chat> chatList = chatRepository.findByChatRoom(post.getChatRoom(), pageable);
        return ResponseEntity.ok(new ChatRoomDto(post.getChatRoom().getId(), chatList));

    }
}
