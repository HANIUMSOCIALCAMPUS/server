package hanium.social_campus.controller.dto.memberDto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class JoinDto {

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;

    @NotBlank
    private String nickName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String sex;

    @NotBlank
    private String university;

    @NotBlank
    private String dept;

    @NotBlank
    private Integer sno;
}
