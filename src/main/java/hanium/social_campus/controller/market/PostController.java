package hanium.social_campus.controller.market;

import hanium.social_campus.controller.dto.ListReturnDto;
import hanium.social_campus.controller.dto.marketDto.PostCreateDto;
import hanium.social_campus.controller.dto.marketDto.PostListDto;
import hanium.social_campus.service.market.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/post")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    //목록 반환
    @GetMapping("/{dealType}")
    public ResponseEntity postList(@PathVariable(name = "dealType") String dealType) {
        return ResponseEntity.ok(new ListReturnDto(postService.postListByDealType(dealType)));
    }

    //게시물 작성
    @PostMapping
    public void createPost(@RequestPart(value = "postReq") PostCreateDto postCreateDto,
                           @RequestPart(name = "images")List<MultipartFile> images) throws IOException {
        postService.createPost(postCreateDto, images);
    }
}
