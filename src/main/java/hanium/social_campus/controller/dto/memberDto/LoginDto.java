package hanium.social_campus.controller.dto.memberDto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {

    @NotBlank
    private String loginId;

    @NotBlank
    private String password;
}
