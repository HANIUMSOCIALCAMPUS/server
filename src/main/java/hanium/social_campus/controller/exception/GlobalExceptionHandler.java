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

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResult RuntimeExceptionHandler(RuntimeException e) {
//        return new ErrorResult(e.getMessage());
//    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResult error(MethodArgumentNotValidException e) {
//        List<ErrorResult> errorList = new ArrayList<>();
//
//        String message = "";
//
//        for (ObjectError error : e.getBindingResult().getAllErrors()) {
//            message = error.getDefaultMessage();
//            errorList.add(new ErrorResult(message));
//        }
//        return new ErrorResult(errorList);
//    }

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResult error(HttpMessageNotReadableException e) {
//        return new ErrorResult("타입이 맞지 않습니다");
//    }

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
