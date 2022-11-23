package hanium.social_campus.service.note;

import hanium.social_campus.auth.config.SecurityUtil;
import hanium.social_campus.controller.dto.marketDto.PostListDto;
import hanium.social_campus.controller.dto.noteDto.NoteDetailDto;
import hanium.social_campus.controller.exception.ApiException;
import hanium.social_campus.controller.exception.ErrorCode;
import hanium.social_campus.domain.Member;
import hanium.social_campus.domain.market.Note;
import hanium.social_campus.domain.market.Post;
import hanium.social_campus.repository.MemberRepository;
import hanium.social_campus.repository.market.NoteRepository;
import hanium.social_campus.repository.market.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;


    /*
    쪽지함 클릭시, 보낸 or 받은 쪽지가 있는 포스트 목록 조회
     */
    public List<PostListDto> getNoteList() {
        Member member = memberRepository.findByLoginId(SecurityUtil.getCurrentMemberId()).orElseThrow(
                () -> new ApiException(ErrorCode.NOT_FOUND_MEMBER)
        );
        List<Note> noteList = noteRepository.findAll(member);
        Set<Post> postList = noteList.stream().map(n -> n.getPost()).collect(Collectors.toSet());
        return postList.stream().map(PostListDto::new).collect(Collectors.toList());
    }

    /*
    쪽지 상세 조회
     */
    public List<NoteDetailDto> getNoteDetail(Long postId) {
        // 멤버 조회
        Member member = memberRepository.findByLoginId(SecurityUtil.getCurrentMemberId()).orElseThrow(
                () -> new ApiException(ErrorCode.NOT_FOUND_MEMBER)
        );
        // 게시물 조회
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ApiException(ErrorCode.NOT_FOUND_MARKET)
        );
        List<Note> notes = noteRepository.findAllWithDetail(member, post);

        return notes.stream().map(
                n -> {
                    if (n.getSender() == member){
                        return new NoteDetailDto(n, "send");
                    }
                    return new NoteDetailDto(n, "receive");
                }
        ).collect(Collectors.toList());

    }

}
