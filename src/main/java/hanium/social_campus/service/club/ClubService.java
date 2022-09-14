package hanium.social_campus.service.club;

import hanium.social_campus.controller.dto.clubDto.ClubCreateDto;
import hanium.social_campus.controller.dto.clubDto.ClubInfoDto;
import hanium.social_campus.controller.dto.clubDto.ClubListInfoDto;
import hanium.social_campus.controller.dto.clubDto.ClubUpdateDto;
import hanium.social_campus.domain.Member;
import hanium.social_campus.domain.group.Club;
import hanium.social_campus.domain.group.ClubType;

import java.util.List;

public interface ClubService {

    /**
     * 클럽 목록
     * 클럽 생성
     * 클럽 참가
     * 클럽 수정
     * 클럽 삭제
     * 클럽 탈퇴
     */

    List<ClubListInfoDto> clubs(ClubType clubType) throws Exception;
    ClubInfoDto createClub(Member member, ClubCreateDto clubCreateDto) throws Exception;
    ClubInfoDto participateClub(Member member, Club club) throws Exception;
    ClubInfoDto updateClub(Member member, Club club, ClubUpdateDto clubUpdateDto) throws Exception;
    void deleteClub(Member member, Club club) throws Exception;
    void leaveClub(Member member, Club club) throws Exception;
}
