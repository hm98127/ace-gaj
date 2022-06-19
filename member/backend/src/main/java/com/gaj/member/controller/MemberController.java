package com.gaj.member.controller;

import com.gaj.member.dto.CommonResponseDto;
import com.gaj.member.dto.social.OAuth2CallbackDto;
import com.gaj.member.service.GoogleService;
import com.gaj.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final GoogleService googleService;

    @GetMapping("/oauth/google")
    public ResponseEntity oAuth2Page() {
        return ResponseEntity.ok(CommonResponseDto.builder()
                        .statusCode(HttpStatus.OK.value())
                        .data(googleService.getLoginPageUrl()).build());
    }

    @GetMapping("/oauth/callbacks/google")
    public ResponseEntity oAuth2Login(OAuth2CallbackDto callback) {

        if (callback.getCode() != null) {
            // 회원가입 이후 닉네임 입력 어떻게 처리?
            return ResponseEntity.ok(CommonResponseDto.builder()
                            .statusCode(HttpStatus.OK.value())
                            .data(memberService.googleLogin(callback.getCode())).build());
        } else {

            return ResponseEntity.badRequest().body(callback.getErrorDescription());
        }
    }

}
