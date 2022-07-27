package hanium.social_campus.service.clubService;

import hanium.social_campus.controller.dto.clubDto.ClubCreateDto;
import hanium.social_campus.controller.dto.clubDto.ClubInfoDto;
import hanium.social_campus.domain.Member;
import hanium.social_campus.domain.group.Club;

public interface ClubService {

    /**
     * 클럽 생성
     * 클럽 참가
     * 클럽 삭제
     */

    ClubInfoDto createClub(Member member, ClubCreateDto clubCreateDto) throws Exception;
    ClubInfoDto participateClub(Member member, Club club) throws Exception;
    void deleteClub(Member member, Club club) throws Exception;
}
