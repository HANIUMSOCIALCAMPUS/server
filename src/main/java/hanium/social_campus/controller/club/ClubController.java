package hanium.social_campus.controller;

import hanium.social_campus.auth.config.SecurityUtil;
import hanium.social_campus.controller.dto.clubDto.ClubCreateDto;
import hanium.social_campus.controller.dto.clubDto.ClubInfoDto;
import hanium.social_campus.controller.dto.clubDto.ClubListInfoDto;
import hanium.social_campus.controller.dto.clubDto.ClubUpdateDto;
import hanium.social_campus.controller.exception.ErrorCode;
import hanium.social_campus.controller.exception.SocialException;
import hanium.social_campus.domain.Member;
import hanium.social_campus.domain.group.Club;
import hanium.social_campus.domain.group.ClubType;
import hanium.social_campus.repository.MemberRepository;
import hanium.social_campus.repository.club.ClubRepository;
import hanium.social_campus.service.club.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class ClubController {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubService clubService;


    public Member getMember(){
        return memberRepository.findByLoginId(SecurityUtil.getCurrentMemberId()).orElseThrow(
                () -> new SocialException(ErrorCode.NOT_FOUND_MEMBER)
        );
    }

    public Club findClub(Long clubId){
        return clubRepository.findById(clubId).orElseThrow(
                () -> new SocialException(ErrorCode.NOT_FOUND_CLUB)
        );
    }

    /**
     * 클럽 목록 반환
     */
    @GetMapping("/clubs/{clubType}")
    public ResponseEntity clubList(@PathVariable(name = "clubType") ClubType clubType){

        List<ClubListInfoDto> clubListInfoDtos = clubService.clubs(clubType);

        return ResponseEntity.ok(clubListInfoDtos);
    }


    /**
     * 키워드로 클럽 검색
     */
    @GetMapping("/club/search")
    public ResponseEntity findClub(@RequestParam(name = "keyword") String keyword,
                                     @RequestParam(name = "offset") int offset){

        List<ClubListInfoDto> clubListInfoDtos = clubService.searchClub(keyword, offset);

        return ResponseEntity.ok(clubListInfoDtos);
    }


    /**
     * 그룹 및 참가자 정보 반환
     */
    @GetMapping("/club/{club_id}/info")
    public ResponseEntity clubInfo(@PathVariable(name = "club_id") Long clubId){

        Club club = findClub(clubId);

        return ResponseEntity.ok(new ClubInfoDto(club));
    }


    /**
     * 클럽 생성
     */
    @PostMapping("/club")
    public ResponseEntity createClub(@RequestBody ClubCreateDto clubCreateDto){

        Member member = getMember();

        ClubInfoDto clubInfoDto = clubService.createClub(member, clubCreateDto);

        return ResponseEntity.ok(clubInfoDto);
    }


    /**
     * 클럽 참가
     */
    @PostMapping("/club/{club_id}")
    public ResponseEntity joinClub(@PathVariable(name = "club_id") Long clubId){

        Member member = getMember();
        Club club = findClub(clubId);

        ClubInfoDto clubInfoDto = clubService.participateClub(member, club);

        return ResponseEntity.ok(clubInfoDto);
    }


    /**
     * 클럽 수정
     */
    @PutMapping("/club/{club_id}")
    public ResponseEntity updateClub(@PathVariable(name = "club_id") Long clubId, @RequestBody ClubUpdateDto clubUpdateDto){

        Member member = getMember();
        Club club = findClub(clubId);

        ClubInfoDto clubInfoDto = clubService.updateClub(member, club, clubUpdateDto);

        return ResponseEntity.ok(clubInfoDto);
    }


    /**
     * 클럽 삭제
     */
    @DeleteMapping("/club/{club_id}")
    public ResponseEntity deleteClub(@PathVariable(name = "club_id") Long clubId){

        Member member = getMember();
        Club club = findClub(clubId);

        clubService.deleteClub(member, club);

        return ResponseEntity.ok(true);
    }


    /**
     * 클럽 탈퇴
     */
    @PostMapping("/club/leave/{club_id}")
    public ResponseEntity leaveClub(@PathVariable(name = "club_id") Long clubId){

        Member member = getMember();
        Club club = findClub(clubId);

        clubService.leaveClub(member, club);

        return ResponseEntity.ok(true);
    }


    /**
     * 내 클럽 목록 반환
     */
    @GetMapping("/clubs")
    public ResponseEntity myClubs(){

        Member member = getMember();

        List<ClubListInfoDto> clubListInfoDtos = clubService.myClubs(member);

        return ResponseEntity.ok(clubListInfoDtos);
    }


    /**
     * 클럽원 강퇴
     */
    @PostMapping("/club/{club_id}/kick-out")
    public ResponseEntity kickOut(@PathVariable(name = "club_id") Long clubId, @RequestBody Map<String, Long> memberId){

        Member member = getMember();
        Member kickMember = memberRepository.findById(memberId.get("memberId")).orElseThrow(
                () -> new SocialException(ErrorCode.NOT_FOUND_MEMBER)
        );
        Club club = findClub(clubId);

        clubService.kickOut(member, kickMember, club);

        return ResponseEntity.ok(new ClubInfoDto(club));
    }


}
