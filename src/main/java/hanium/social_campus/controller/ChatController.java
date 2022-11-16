package hanium.social_campus.controller;

import hanium.social_campus.auth.config.SecurityUtil;
import hanium.social_campus.auth.jwt.TokenProvider;
import hanium.social_campus.chat.RedisPublisher;
import hanium.social_campus.controller.dto.chatDto.ChatMessageResponseDto;
import hanium.social_campus.controller.dto.chatDto.SendMessageDto;
import hanium.social_campus.controller.exception.ErrorCode;
import hanium.social_campus.controller.exception.SocialException;
import hanium.social_campus.domain.Member;
import hanium.social_campus.domain.chat.Chat;
import hanium.social_campus.repository.MemberRepository;
import hanium.social_campus.service.chatService.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final RedisPublisher redisPublisher;
    private final ChatService chatService;


    public Member getMember(){
        return memberRepository.findByLoginId(SecurityUtil.getCurrentMemberId()).orElseThrow(
                () -> new SocialException(ErrorCode.NOT_FOUND_MEMBER)
        );
    }

    @MessageMapping("/club/message")    // pub/club/message
    public void sendMessage(@RequestBody SendMessageDto sendMessageDto, SimpMessageHeaderAccessor accessor){
        log.info("메시지: {}", sendMessageDto.getMessage());

        // stomp
        String jwtToken = accessor.getFirstNativeHeader("Authorization").substring(7);
        if (tokenProvider.validateToken(jwtToken)) {
            Authentication authentication = tokenProvider.getAuthentication(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        Member member = getMember();

        Chat chat = chatService.create(member, sendMessageDto);

        redisPublisher.publish(new ChannelTopic("/sub/chat/" + sendMessageDto.getRoomId()), new ChatMessageResponseDto(chat));

    }




}
