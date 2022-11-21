package hanium.social_campus.controller.market;

import hanium.social_campus.service.note.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user/note")
@RestController
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping
    public ResponseEntity getNoteList() {
        return ResponseEntity.ok(noteService.getNoteList());
    }

    @GetMapping("{post_id}")
    public ResponseEntity getNoteDetail(@PathVariable("post_id") Long id) {
        return ResponseEntity.ok(noteService.getNoteDetail(id));
    }
}
