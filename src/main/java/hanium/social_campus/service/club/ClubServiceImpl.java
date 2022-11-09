package hanium.social_campus.service.club;

import hanium.social_campus.controller.dto.clubDto.ClubCreateDto;
import hanium.social_campus.controller.dto.clubDto.ClubInfoDto;
import hanium.social_campus.controller.dto.clubDto.ClubListInfoDto;
import hanium.social_campus.controller.dto.clubDto.ClubUpdateDto;
import hanium.social_campus.controller.dto.memberDto.MemberInfoDto;
import hanium.social_campus.controller.exception.ErrorCode;
import hanium.social_campus.controller.exception.SocialException;
import hanium.social_campus.domain.Member;
import hanium.social_campus.domain.chat.ChatRoom;
import hanium.social_campus.domain.group.Club;
import hanium.social_campus.domain.group.ClubType;
import hanium.social_campus.domain.group.Participation;
import hanium.social_campus.repository.club.ClubRepository;
import hanium.social_campus.repository.club.ParticipationRepository;
import hanium.social_campus.repository.ChatRoomRepository;
import hanium.social_campus.service.chatService.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClubServiceImpl implements ClubService{

    private final ParticipationRepository participationRepository;
    private final ChatRoomService chatRoomService;
    private final ClubRepository clubRepository;
    private final ChatRoomRepository chatRoomRepository;


    @Override
    public List<ClubListInfoDto> clubs(ClubType clubType) {

        return clubRepository.findByClubType(clubType).stream()
                .map(Club :: toClubListInfoDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<ClubListInfoDto> searchClub(String keyword, int offset) {

        return clubRepository.findWithKeyword(keyword, offset).stream()
                .map(Club :: toClubListInfoDto)
                .collect(Collectors.toList());
    }


    @Override
    public ClubInfoDto clubInfo(Club club) {

        List<MemberInfoDto> memberInfoDtos = participationRepository.findByClubId(club.getId()).stream()
                .map(Participation::getMember)
                .map(Member::toMemberInfoDto)
                .collect(Collectors.toList());

        return new ClubInfoDto(club, memberInfoDtos);
    }


    @Override
    public ClubInfoDto createClub(Member member, ClubCreateDto clubCreateDto) {

        ChatRoom chatRoom = chatRoomService.createChatRoom();
        Club club = Club.create(clubCreateDto, chatRoom);

        // TransientPropertyValueException 문제를 해결하기 위함
        clubRepository.save(club);

        // 그룹 생성자도 참가시켜줌
        return participateClub(member, club);

    }


    @Override
    public ClubInfoDto participateClub(Member member, Club club) {
        if (participationRepository.findByClubIdAndMemberId(club.getId(), member.getId()).isPresent()){   // 이미 가입된 상태

        } else if (club.getMaximumCount() == participationRepository.findByClubId(club.getId()).size()) {   // 그룹이 꽉 찬 상태
            throw new SocialException(ErrorCode.CLUB_JOIN_FULL);
        } else {
            Participation participation = Participation.create(member, club);   // 참가 정보 생성
            participationRepository.save(participation);    // 참가 정보 저장
        }

        return clubInfo(club);
    }


    @Override
    public ClubInfoDto updateClub(Member member, Club club, ClubUpdateDto clubUpdateDto) {

        List<Participation> participations = participationRepository.findByClubId(club.getId());

        if (member.getId() != participationRepository.findMemberIdByClubId(club.getId())){  // 방장의 경우에만 수정 가능
            throw new SocialException(ErrorCode.CLUB_OWNER_RIGHT);
        } else if(participations.size() > clubUpdateDto.getMaximumCount()) {
            throw new SocialException(ErrorCode.CLUB_NOT_MODIFY);
        }

        // 그룹 정보 수정
        club.updateClub(clubUpdateDto.getClubName(), clubUpdateDto.getMaximumCount(), clubUpdateDto.getClubType());

        return clubInfo(club);
    }


    @Override
    public void deleteClub(Member member, Club club) {

        // 방장의 경우 그룹 및 참가 삭제
        if (member.getId() == participationRepository.findMemberIdByClubId(club.getId())){
            // 참가 삭제 후 그룹 삭제
            for (Participation participation : participationRepository.findByClubId(club.getId()))
                participationRepository.delete(participation);

            chatRoomRepository.delete(club.getChatRoom());
            clubRepository.delete(club);
        } else {
            throw new SocialException(ErrorCode.CLUB_OWNER_RIGHT);
        }
    }


    @Override
    public void leaveClub(Member member, Club club) {

        Participation participation = participationRepository.findByClubIdAndMemberId(club.getId(), member.getId()).orElseThrow(
                () -> new SocialException(ErrorCode.CLUB_NOT_JOINED)
        );

        // 방장이 탈퇴하면 다음으로 참가한 사람이 새로운 방장이 됨
        participationRepository.delete(participation);

        // 모든 인원이 나갈 경우 그룹 삭제
        if (participationRepository.findByClubId(club.getId()).size() == 0) {
            chatRoomRepository.delete(club.getChatRoom());
            clubRepository.delete(club);
        }
    }


    @Override
    public List<ClubListInfoDto> myClubs(Member member) {

        List<Participation> participations = participationRepository.findByMember(member);
        List<ClubListInfoDto> clubListInfoDtos = new ArrayList<>();

        for (Participation participation : participations)
            clubListInfoDtos.add(new ClubListInfoDto(participation.getClub()));

        return clubListInfoDtos;
    }


    @Override
    public void kickOut(Member member, Member kickMember, Club club) {
        if (member.getId() != participationRepository.findMemberIdByClubId(club.getId())){
            throw new SocialException(ErrorCode.CLUB_OWNER_RIGHT);
        }

        Participation participation = participationRepository.findByClubIdAndMemberId(club.getId(), kickMember.getId()).orElseThrow(
                () -> new SocialException(ErrorCode.CLUB_NOT_JOINED)
        );

        participationRepository.delete(participation);
    }

}
