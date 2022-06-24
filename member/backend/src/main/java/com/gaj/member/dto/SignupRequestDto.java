package com.gaj.member.dto;

import com.gaj.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class SignupRequestDto {

    @NotBlank(message = "이메일은 공백일 수 없습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
    private String password;

    @NotBlank(message = "닉네임은 공백일 수 없습니다.")
    private String nickname;

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }
}
