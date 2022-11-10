package hanium.social_campus.controller.dto.memberDto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class JoinDto {

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String sex;

    @NotBlank
    private String university;

    @NotBlank
    private String dept;

    @NotNull
    private Integer sno;
}
