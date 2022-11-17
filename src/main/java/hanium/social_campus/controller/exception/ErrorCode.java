package hanium.social_campus.controller.exception;

import lombok.Getter;

/**
 * error code 관리용 (추후 개발 예정)
 */
@Getter
public enum ErrorCode {

    TOKEN_EXPIRED(1000, "토큰이 만료되었습니다"),

    // member
    NOT_FOUND_MEMBER(1100, "존재하지 않는 회원입니다"),

    // club
    NOT_FOUND_CLUB(1200, "그룹이 존재하지 않습니다"),
    CLUB_JOIN_FULL(1201, "인원이 가득 찼습니다"),
    CLUB_ALREADY_JOINED(1202, "이미 그룹에 소속되어 있습니다"),
    CLUB_OWNER_RIGHT(1203, "방장 권한입니다"),
    CLUB_NOT_JOINED(1204, "그룹에 소속되어 있지 않습니다"),
    CLUB_NOT_MODIFY(1205, "최대 인원수를 현재 인원수 이하로 변경할 수 없습니다"),

    // chat
    NOT_FOUND_CHATROOM(1300, "채팅방을 찾을 수 없습니다"),

    // etc
    RUNTIME(100, "런타임에러"),
    ENTITY_NOT_FOUND(200, "찾을 수 없는 엔티티"),
    BAD_CREDENTIALS(300, "로그인 실패"),

    // market
    NOT_FOUND_MARKET(1400, "게시물을 찾을 수 없습니다"),
    NOT_EXIT_POSTS(1401, "조회결과가 없습니다"),
    ALREADY_DELETE(1402, "삭제처리된 게시물입니다"),
    NOT_EXIT_IMAGE(1403, "이미지가 존재하지 않습니다");


    private final int code;
    private final String message;


    ErrorCode(int code,String message) {
        this.code = code;
        this.message = message;
    }
}
