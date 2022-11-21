package hanium.social_campus.controller.dto.noteDto;

import hanium.social_campus.domain.market.Note;
import lombok.Data;

@Data
public class NoteDetailDto {

    private Long id;

    private String message;

    private String sendStatus;

    public NoteDetailDto(Note note, String status) {
        id = note.getId();
        message = note.getMessage();
        sendStatus = status;
    }
}
