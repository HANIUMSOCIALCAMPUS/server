package hanium.social_campus.service.market;

import hanium.social_campus.auth.config.SecurityUtil;
import hanium.social_campus.config.s3.S3Uploader;
import hanium.social_campus.controller.dto.marketDto.PostCreateDto;
import hanium.social_campus.controller.dto.marketDto.PostListDto;
import hanium.social_campus.controller.exception.ApiException;
import hanium.social_campus.controller.exception.ErrorCode;
import hanium.social_campus.domain.Member;
import hanium.social_campus.domain.group.ClubType;
import hanium.social_campus.domain.market.DealType;
import hanium.social_campus.domain.market.Post;
import hanium.social_campus.domain.market.PostImage;
import hanium.social_campus.repository.MemberRepository;
import hanium.social_campus.repository.market.PostImageRepository;
import hanium.social_campus.repository.market.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static hanium.social_campus.controller.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final MemberRepository memberRepository;
    private final S3Uploader s3Uploader;
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;

    //post 리스트 반환
    public List<PostListDto> postListByDealType(String dealType) {
        return null;
    }

    //post 작성
    public void createPost(PostCreateDto postCreateDto, List<MultipartFile> images) throws IOException {
//        Member member = memberRepository.findByLoginId(SecurityUtil.getCurrentMemberId()).orElseThrow(
//                () -> new ApiException(NOT_FOUND_MEMBER)
//        );
        List<String> imageUrls = s3Uploader.uploadFiles(images, "post");
        log.info(imageUrls.get(0));
        List<PostImage> saveImages = new ArrayList<>();
        for (String imageUrl : imageUrls) {
            saveImages.add(new PostImage(imageUrl));
        }
        DealType dealType = DealType.from(postCreateDto.getDealType());
        Post post = Post.create(null, postCreateDto.getTitle(), dealType, postCreateDto.getDescription(), postCreateDto.getPrice(), saveImages);
        postRepository.save(post);
    }


}
