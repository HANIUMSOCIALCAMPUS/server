package hanium.social_campus.controller.dto.chatDto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SendMessageDto {

    private Long roomId;
    private String sender;
    private String message;

}
