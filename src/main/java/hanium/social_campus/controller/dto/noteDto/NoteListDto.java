package hanium.social_campus.controller.dto.noteDto;

import hanium.social_campus.domain.market.Note;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteListDto {

    private Long id;

    private String message;

    private LocalDateTime sendTime;

    public NoteListDto(Note note) {
        id = note.getId();
        message = note.getMessage();
        sendTime = note.getCreateAt();
    }
}
