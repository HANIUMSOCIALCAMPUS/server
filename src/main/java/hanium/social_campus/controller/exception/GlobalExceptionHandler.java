package hanium.social_campus.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 로그인 실패
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult BadCredentialsExceptionHandler(BadCredentialsException e) {
        return new ErrorResult(300, "로그인 실패");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResult ApiException(ApiException e) {
        return new ErrorResult(e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }


    @Getter
    @AllArgsConstructor
    static class ErrorResult{
        private int code;
        private String message;
    }
}
