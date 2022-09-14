package hanium.social_campus.service.club;

import hanium.social_campus.controller.dto.clubDto.ClubCreateDto;
import hanium.social_campus.controller.dto.clubDto.ClubInfoDto;
import hanium.social_campus.controller.dto.clubDto.ClubListInfoDto;
import hanium.social_campus.controller.dto.clubDto.ClubUpdateDto;
import hanium.social_campus.controller.dto.memberDto.MemberInfoDto;
import hanium.social_campus.domain.Member;
import hanium.social_campus.domain.group.Club;
import hanium.social_campus.domain.group.ClubType;
import hanium.social_campus.domain.group.Participation;
import hanium.social_campus.repository.club.ClubRepository;
import hanium.social_campus.repository.club.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ClubServiceImpl implements ClubService{

    private final ParticipationRepository participationRepository;
    private final ClubRepository clubRepository;


    @Override
    public List<ClubListInfoDto> clubs(ClubType clubType) throws Exception {

        List<Club> clubList = clubRepository.findByClubType(clubType);
        List<ClubListInfoDto> clubListInfoDtos = new ArrayList<>();

        for (Club club : clubList){
            clubListInfoDtos.add(ClubListInfoDto.builder()
                    .clubName(club.getClubName())
                    .currentCount(club.getParticipations().size())
                    .maximumCount(club.getMaximumCount())
                    .clubType(clubType)
                    .build());
        }

        return clubListInfoDtos;
    }


    @Override
    public ClubInfoDto createClub(Member member, ClubCreateDto clubCreateDto) throws Exception {

        Club club = Club.create(clubCreateDto);

        // TransientPropertyValueException 문제를 해결하기 위함
        clubRepository.save(club);

        // 그룹 생성자도 참가시켜줌
        return participateClub(member, club);

    }


    @Override
    public ClubInfoDto participateClub(Member member, Club club) throws Exception {

        if (participationRepository.existsByClubIdAndMemberId(club.getId(), member.getId())){   // 이미 가입된 상태
            throw new Exception("해당 클럽에 가입된 상태입니다.");
        } else if (club.getMaximumCount() == participationRepository.findByClubId(club.getId()).size()) {   // 그룹이 꽉 찬 상태
            throw new Exception("클럽에 더 이상 참여할 수 없습니다.");
        } else {
            Participation participation = Participation.create(member, club);   // 참가 정보 생성
            participationRepository.save(participation);    // 참가 정보 저장
        }

        List<Participation> participations = participationRepository.findByClubId(club.getId());
        List<MemberInfoDto> memberInfoDtos = new ArrayList<>();

        // 그룹 참가자들 조회
        for (Participation eachParticipation: participations) {
            memberInfoDtos.add(MemberInfoDto.builder()
                    .nickName(eachParticipation.getMember().getNickName())
                    .sex(eachParticipation.getMember().getSex())
                    .dept(eachParticipation.getMember().getDept())
                    .sno(eachParticipation.getMember().getSno())
                    .build()
            );
        }

        return new ClubInfoDto(club.getClubName(), club.getMaximumCount(), club.getClubType(), memberInfoDtos);
    }


    @Override
    public ClubInfoDto updateClub(Member member, Club club, ClubUpdateDto clubUpdateDto) throws Exception {

        List<Participation> participations = participationRepository.findByClubId(club.getId());

        if (member.getId() != participationRepository.findMemberIdByClubId(club.getId())){  // 방장의 경우에만 수정 가능
            throw new Exception("방장인 사용자만 수정이 가능합니다.");
        } else if(participations.size() > clubUpdateDto.getMaximumCount()) {
            throw new Exception("최대 인원수를 현재 인원수 이하로 변경할 수 없습니다.");
        }

        // 그룹 정보 수정
        club.updateClub(clubUpdateDto.getClubName(), clubUpdateDto.getMaximumCount(), clubUpdateDto.getClubType());
        List<MemberInfoDto> memberInfoDtos = new ArrayList<>();

        // 그룹 참가자들 조회
        for (Participation eachParticipation: participations) {
            memberInfoDtos.add(MemberInfoDto.builder()
                    .nickName(eachParticipation.getMember().getNickName())
                    .sex(eachParticipation.getMember().getSex())
                    .dept(eachParticipation.getMember().getDept())
                    .sno(eachParticipation.getMember().getSno())
                    .build()
            );
        }

        // 그룹 관련 변경된 정보 전달 (참가자들 정보도 다시 전달해야 하는지 고민)
        return new ClubInfoDto(club.getClubName(), club.getMaximumCount(), club.getClubType(), memberInfoDtos);
    }


    @Override
    public void deleteClub(Member member, Club club) throws Exception {

        // 방장의 경우 그룹 및 참가 삭제
        if (member.getId() == participationRepository.findMemberIdByClubId(club.getId())){
            // 참가 삭제 후 그룹 삭제
            for (Participation participation : participationRepository.findByClubId(club.getId())){
                participationRepository.delete(participation);
            }
            clubRepository.delete(club);
        } else {
            throw new Exception("방장인 사용자만 클럽 삭제가 가능합니다.");
        }
    }


    @Override
    public void leaveClub(Member member, Club club) throws Exception {

        // 방장이 탈퇴할 경우 클럽 삭제
        if (member.getId() == participationRepository.findMemberIdByClubId(club.getId())){
            deleteClub(member, club);
        } else if (participationRepository.existsByClubIdAndMemberId(club.getId(), member.getId())) {
            Participation participation = participationRepository.findByClubIdAndMemberId(club.getId(), member.getId());
            participationRepository.delete(participation);
        } else {
            throw new Exception("클럽에 가입되어 있지 않습니다.");
        }

    }


}
