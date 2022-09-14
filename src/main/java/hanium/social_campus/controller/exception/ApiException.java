package hanium.social_campus.controller.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiException extends RuntimeException{

    private final ErrorCode errorCode;

}
