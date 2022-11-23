package hanium.social_campus.service.market;

import hanium.social_campus.auth.config.SecurityUtil;
import hanium.social_campus.config.s3.S3Uploader;
import hanium.social_campus.controller.dto.marketDto.*;
import hanium.social_campus.controller.dto.noteDto.NoteDetailDto;
import hanium.social_campus.controller.exception.ApiException;
import hanium.social_campus.controller.exception.ErrorCode;
import hanium.social_campus.domain.Member;
import hanium.social_campus.domain.chat.ChatRoom;
import hanium.social_campus.domain.group.ClubType;
import hanium.social_campus.domain.market.*;
import hanium.social_campus.repository.MemberRepository;
import hanium.social_campus.repository.market.NoteRepository;
import hanium.social_campus.repository.market.PostImageRepository;
import hanium.social_campus.repository.market.PostRepository;
import hanium.social_campus.service.chatService.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final ChatRoomService chatRoomService;
    private final NoteRepository noteRepository;

    //post 리스트 반환
    public List<PostListDto> postListByDealType(String dealType) {
        List<Post> posts = postRepository.findByDealType(DealType.from(dealType));
        if (posts.size() == 0) {
            return new ArrayList<>();
        }
        List<PostListDto> returnPosts = posts.stream().map(PostListDto::new).collect(Collectors.toList());
        return returnPosts;
    }

    // post 상세 조회
    public PostDetailDto getPostDetail(Long id) {
        Post post = postRepository.findByIdDetail(id).orElseThrow(
                () -> new ApiException(NOT_FOUND_MARKET)
        );
        return new PostDetailDto(post);
    }

    // post 작성
    public void createPost(PostCreateDto postCreateDto, List<MultipartFile> images) throws IOException {

        Member member = memberRepository.findByLoginId(SecurityUtil.getCurrentMemberId()).orElseThrow(
                () -> new ApiException(NOT_FOUND_MEMBER)
        );

        List<PostImage> saveImages = new ArrayList<>();

        // 이미지가 존재하지 않을 경우
        if (images == null) {
            saveImages.add(new PostImage(DefaultImageEnv.DEFAULT_IMAGE_URL));
        }else{
            List<String> imageUrls = s3Uploader.uploadFiles(images, "post");
            for (String imageUrl : imageUrls) {
                saveImages.add(new PostImage(imageUrl));
            }
        }

        savePost(member, postCreateDto, saveImages);
    }

    // post 저장
    public void savePost(Member member, PostCreateDto postCreateDto, List<PostImage> saveImages) {
        DealType dealType = DealType.from(postCreateDto.getDealType());
        ChatRoom chatRoom = chatRoomService.createChatRoom();
        Post post = Post.create(member, chatRoom, postCreateDto.getTitle(), dealType, postCreateDto.getDescription(), postCreateDto.getPrice(), saveImages);
        postRepository.save(post);
    }

    // post 수정
    @Transactional
    public void editPost(Long id, PostEditDto postEditDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ApiException(NOT_FOUND_MARKET)
        );
        post.edit(postEditDto.getTitle(), postEditDto.getDescription(), postEditDto.getPrice());
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ApiException(NOT_FOUND_MARKET)
        );
        postRepository.delete(post);
    }

    public List<PostListDto> searchPost(String title) {
        List<Post> byTitleSearch = postRepository.findByTitleSearch(title);
        if (byTitleSearch.isEmpty()) {
            return new ArrayList<>();
        }
        return byTitleSearch.stream().map(PostListDto::new).collect(Collectors.toList());
    }

    /*
    쪽지 보내기
     */
    public NoteDetailDto sendNote(Long id, PostNoteDto postNoteDto) {
        // 보낸 사람
        Member member = memberRepository.findByLoginId(SecurityUtil.getCurrentMemberId()).orElseThrow(
                () -> new ApiException(NOT_FOUND_MEMBER)
        );

        // 게시물
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ApiException(NOT_FOUND_MARKET)
        );

        Note note = Note.builder()
                .post(post)
                .member(member)
                .message(postNoteDto.getMessage())
                .build();

        noteRepository.save(note);

        return new NoteDetailDto(note, "send");

    }


}
