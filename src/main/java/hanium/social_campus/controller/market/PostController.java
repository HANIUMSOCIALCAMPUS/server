package hanium.social_campus.controller.market;

import hanium.social_campus.controller.dto.marketDto.PostCreateDto;
import hanium.social_campus.controller.dto.marketDto.PostDetailDto;
import hanium.social_campus.controller.dto.marketDto.PostEditDto;
import hanium.social_campus.controller.dto.marketDto.PostListDto;
import hanium.social_campus.service.market.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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
                           @RequestPart(name = "images")List<MultipartFile> images) throws IOException {
        log.info(postCreateDto.toString());
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


}
