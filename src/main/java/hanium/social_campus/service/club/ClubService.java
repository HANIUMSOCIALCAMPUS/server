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
     * @param clubType
     * @return List<ClubListInfoDto>
     */
    List<ClubListInfoDto> clubs(ClubType clubType);

    /**
     * 클럽 검색
     * @param keyword
     * @param offset
     * @return
     */
    List<ClubListInfoDto> searchClub(String keyword, int offset);

    /**
     * 클럽 정보
     * @param club
     * @return
     */
    ClubInfoDto clubInfo(Club club);

    /**
     * 클럽 생성
     * @param member
     * @param clubCreateDto
     * @return ClubInfoDto
     */
    ClubInfoDto createClub(Member member, ClubCreateDto clubCreateDto);

    /**
     * 클럽 참가
     * @param member
     * @param club
     * @return
     */
    ClubInfoDto participateClub(Member member, Club club);

    /**
     * 클럽 수정
     * @param member
     * @param club
     * @param clubUpdateDto
     * @return ClubInfoDto
     */
    ClubInfoDto updateClub(Member member, Club club, ClubUpdateDto clubUpdateDto);

    /**
     * 클럽 삭제
     * @param member
     * @param club
     */
    void deleteClub(Member member, Club club);

    /**
     * 클럽 탈퇴
     * @param member
     * @param club
     */
    void leaveClub(Member member, Club club);

    /**
     * 내 클럽 목록
     * @param member
     * @return ClubListInfoDto
     */
    List<ClubListInfoDto> myClubs(Member member);

    /**
     * 클럽원 강퇴
     * @param member
     * @param kickMember
     * @param club
     */
    void kickOut(Member member, Member kickMember, Club club);

}
