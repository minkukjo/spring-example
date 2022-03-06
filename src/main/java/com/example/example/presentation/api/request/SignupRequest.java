package com.example.example.presentation.api.request;

import com.example.example.presentation.validator.EmailUnique;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SignupRequest {
    @NotBlank
    @Size(max = 20)
    @EmailUnique(message = "이미 존재하는 이메일 주소입니다.")
    @Email(message = "이메일 형식을 맞춰주세요.")
    private String email;
    @NotBlank
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{10,}$", message = "비밀번호는 최소 10글자 이상, 영어 대문자, 영어 소문자, 특수 문자, 숫자 각 1개 이상 포함이 되어야 합니다.")
    private String password;
    @NotBlank
    @Size(max = 20, message = "최대 20글자만 가능합니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z]+$", message = "한글, 영문 대소문자만 허용됩니다.")
    private String name;
    @NotBlank
    @Size(max = 30, message = "최대 30글자만 가능합니다.")
    @Pattern(regexp = "^[a-z]+$", message = "영문 소문자만 허용됩니다.")
    private String nickname;
    @Size(max = 20, message = "최대 20글자만 가능합니다.")
    @NotBlank
    private String phoneNumber;
    private String gender;
}
