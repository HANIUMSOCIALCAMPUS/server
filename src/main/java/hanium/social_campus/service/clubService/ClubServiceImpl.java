package hanium.social_campus.service.clubService;

import hanium.social_campus.controller.dto.clubDto.ClubCreateDto;
import hanium.social_campus.controller.dto.clubDto.ClubInfoDto;
import hanium.social_campus.controller.dto.memberDto.MemberInfoDto;
import hanium.social_campus.domain.Member;
import hanium.social_campus.domain.group.Club;
import hanium.social_campus.domain.group.Participation;
import hanium.social_campus.repository.ParticipationRepository;
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

    @Override
    public ClubInfoDto createClub(Member member, ClubCreateDto clubCreateDto) throws Exception {

        Club club = Club.create(clubCreateDto);

        // 그룹 생성자도 참가시켜줌
        return participateClub(member, club);

    }

    @Override
    public ClubInfoDto participateClub(Member member, Club club) throws Exception {

        Participation participation = Participation.builder()
                .member(member)
                .club(club)
                .build();

        // 참가 정보 저장
        participationRepository.save(participation);

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
    public void deleteClub(Member member, Club club) throws Exception {

        if (member.getId() == participationRepository.findMemberIdByClubId(club.getId())){
            participationRepository.deleteByClubId(club.getId());
        } else {
            new Exception("방장인 사용자만 그룹 삭제가 가능합니다");
        }

    }
}
