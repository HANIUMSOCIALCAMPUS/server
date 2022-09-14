package hanium.social_campus.service.market;

import hanium.social_campus.auth.config.SecurityUtil;
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

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;

    //post 리스트 반환
    public List<PostListDto> postListByDealType(String dealType) {
        DealType findDealType = DealType.from(dealType);
        return postRepository.findByDealType(findDealType)
                .stream()
                .map(post -> new PostListDto(post))
                .collect(Collectors.toList());
    }

    //post 작성
    public void createPost(PostCreateDto postCreateDto) {
        Member member = memberRepository.findByLoginId(SecurityUtil.getCurrentMemberId()).orElseThrow(
                () -> new ApiException(ErrorCode.ENTITY_NOT_FOUND)
        );
        log.info("================");
        log.info("postImageUrls = {}", postCreateDto.getPostImageUrl());
        List<PostImage> postImages = postCreateDto.getPostImageUrl()
                .stream().map(PostImage::new).collect(Collectors.toList());
        log.info("=================");

        Post post = Post.create(member, postCreateDto.getTitle(), DealType.from(postCreateDto.getDealType()), postCreateDto.getDescription(), postCreateDto.getPrice(), postImages);
        postRepository.save(post);

    }


}
