package hanium.social_campus.controller.dto.chatDto;

import hanium.social_campus.domain.chat.Chat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatDto {
    private Long chatId;

    private String sender;

    private String message;

    private LocalDateTime sendTime;

    public ChatDto(Chat chat) {
        this.chatId = chat.getId();
        this.sender = chat.getSender();
        this.message = chat.getMessage();
        this.sendTime = chat.getCreateAt();
    }
}
