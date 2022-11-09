package hanium.social_campus.controller.dto.chatDto;

import hanium.social_campus.domain.chat.Chat;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ChatRoomDto {
    private Long chatRoomId;

    private List<ChatDto> chats;

    public ChatRoomDto(Long chatRoomId, List<Chat> chats){
        this.chatRoomId = chatRoomId;
        this.chats = chats.stream().map(chat -> new ChatDto(chat)).collect(Collectors.toList());
    }
}
