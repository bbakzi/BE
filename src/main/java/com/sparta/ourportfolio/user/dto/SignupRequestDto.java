package com.sparta.ourportfolio.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
public class SignupRequestDto {

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$",
            message = "비밀번호는 최소 6글자 이상이며 알파벳 대소문자와 숫자로 구성되어야 합니다.")
    private String  password;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]{1,10}$", message = "닉네임은 10글자 이하이며, 영어 또는 한글, 숫자로 구성되어야 합니다. ")
    private String nickname;

    private String profileImage;

}