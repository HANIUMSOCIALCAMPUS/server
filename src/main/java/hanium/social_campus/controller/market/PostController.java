package hanium.social_campus.controller.market;

import hanium.social_campus.controller.dto.ListReturnDto;
import hanium.social_campus.controller.dto.marketDto.PostCreateDto;
import hanium.social_campus.controller.dto.marketDto.PostListDto;
import hanium.social_campus.service.market.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    //목록 반환
    @GetMapping("/post/{dealType}")
    public ResponseEntity postList(@PathVariable(name = "dealType") String dealType) {
        return ResponseEntity.ok(new ListReturnDto(postService.postListByDealType(dealType)));
    }

    //게시물 작성
    @PostMapping("/post")
    public void createPost(@RequestBody PostCreateDto postCreateDto) {
        postService.createPost(postCreateDto);
    }
}
