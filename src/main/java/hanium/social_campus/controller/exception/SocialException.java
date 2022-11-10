package hanium.social_campus.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SocialException extends RuntimeException{

    private final ErrorCode errorCode;

}
