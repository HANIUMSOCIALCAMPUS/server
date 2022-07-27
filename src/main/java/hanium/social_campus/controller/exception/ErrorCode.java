package hanium.social_campus.controller.exception;

import lombok.Data;
import lombok.Getter;

/**
 * error code 관리용 (추후 개발 예정)
 */
@Getter
public enum ErrorCode {

    RUNTIME("런타임에러"),
    ENTITY_NOT_FOUND("찾을 수 없는 엔티티"),
    BAD_CREDENTIALS("로그인 실패");



    private String message;


    private ErrorCode(String message) {
        this.message = message;
    }
}
