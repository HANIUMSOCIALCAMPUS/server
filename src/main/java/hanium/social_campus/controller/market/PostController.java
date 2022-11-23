package hanium.social_campus.controller.market;

import hanium.social_campus.controller.dto.marketDto.*;
import hanium.social_campus.controller.exception.ApiException;
import hanium.social_campus.service.market.PostService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static hanium.social_campus.controller.exception.ErrorCode.NOT_EXIT_IMAGE;

@RequestMapping("/user/post")
@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 목록 반환 - 페이징 고민
    @GetMapping
    public ResponseEntity<List<PostListDto>> getPostList(@RequestParam("dealType") String dealType) {
        return ResponseEntity.ok(postService.postListByDealType(dealType));
    }

    // 게시물 작성
    @PostMapping
    public void createPost(@RequestPart(value = "postReq") PostCreateDto postCreateDto,
                           @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {
        postService.createPost(postCreateDto, images);
    }

    // 상세 조회
    @GetMapping("/{post_id}")
    public ResponseEntity<PostDetailDto> getPostDetail(@PathVariable("post_id") Long id) {
        return ResponseEntity.ok(postService.getPostDetail(id));
    }

    // 수정
    @PutMapping("/{post_id}")
    public void editPost(@PathVariable("post_id") Long id, @RequestBody PostEditDto postEditDto) {
        postService.editPost(id, postEditDto);
    }

    // 삭제 - 아직 구현 x
    @DeleteMapping("/{post_id}")
    public void deletePost(@PathVariable("post_id") Long id) {
        postService.deletePost(id);
    }

    // 검색 - 제목
    @GetMapping("/search")
    public ResponseEntity<List<PostListDto>> searchPost(@RequestParam("value") String value) {
        return ResponseEntity.ok(postService.searchPost(value));
    }

    // 쪽지 작성
    @PostMapping("{post_id}/note")
    public ResponseEntity sendNote(@PathVariable("post_id") Long id, @RequestBody PostNoteDto postNoteDto) {
        return ResponseEntity.ok(postService.sendNote(id, postNoteDto));
    }


}
