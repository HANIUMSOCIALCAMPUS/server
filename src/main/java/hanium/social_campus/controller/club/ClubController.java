package hanium.social_campus.controller.club;

import hanium.social_campus.auth.config.SecurityUtil;
import hanium.social_campus.controller.dto.clubDto.ClubCreateDto;
import hanium.social_campus.controller.dto.clubDto.ClubInfoDto;
import hanium.social_campus.controller.exception.ErrorCode;
import hanium.social_campus.domain.Member;
import hanium.social_campus.domain.group.Club;
import hanium.social_campus.repository.ClubRepository;
import hanium.social_campus.repository.MemberRepository;
import hanium.social_campus.service.clubService.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class ClubController {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ClubService clubService;

    /**
     * 클럽 생성
     */
    @PostMapping("/club")
    public ResponseEntity createClub(@RequestBody ClubCreateDto clubCreateDto){

        try{
            Member member = memberRepository.findByLoginId(SecurityUtil.getCurrentMemberId()).orElseThrow(
                    () -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND.getMessage())
            );

            ClubInfoDto clubInfoDto = clubService.createClub(member, clubCreateDto);

            return new ResponseEntity(clubInfoDto, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


    /**
     * 클럽 참가
     */
    @PostMapping("/club/{club_id}")
    public ResponseEntity joinClub(@PathVariable(name = "club_id") Long clubId){

        try {
            Member member = memberRepository.findByLoginId(SecurityUtil.getCurrentMemberId()).orElseThrow(
                    () -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND.getMessage())
            );

            Club club = clubRepository.findById(clubId).orElseThrow(
                    () -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND.getMessage())
            );

            ClubInfoDto clubInfoDto = clubService.participateClub(member, club);

            return new ResponseEntity(clubInfoDto, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }


    /**
     * 클럽 삭제
     */
    @DeleteMapping("/club/{club_id}")
    public ResponseEntity deleteClub(@PathVariable(name = "club_id") Long clubId){

        try {
            Member member = memberRepository.findByLoginId(SecurityUtil.getCurrentMemberId()).orElseThrow(
                    () -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND.getMessage())
            );

            Club club = clubRepository.findById(clubId).orElseThrow(
                    () -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND.getMessage())
            );

            clubService.deleteClub(member, club);

            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
