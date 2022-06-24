package com.gaj.member.controller;

import com.gaj.member.domain.Member;
import com.gaj.member.dto.LoginRequestDto;
import com.gaj.member.dto.RestResponse;
import com.gaj.member.dto.SignupRequestDto;
import com.gaj.member.dto.social.OAuth2CallbackDto;
import com.gaj.member.service.GoogleService;
import com.gaj.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final GoogleService googleService;

    @PostMapping("/login")
    public ResponseEntity selfLogin(@Valid LoginRequestDto dto) {

        Member member = dto.toEntity();
        return ResponseEntity.ok(RestResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(memberService.login(member)).build());
    }

    @PostMapping("/signup")
    public ResponseEntity selfSignup(@Valid SignupRequestDto dto) {

        Member member = dto.toEntity();
        return ResponseEntity.ok(RestResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message(HttpStatus.OK.getReasonPhrase())
                    .data(memberService.signup(member)).build());
    }

    @GetMapping("/oauth/google")
    public ResponseEntity oAuth2Page() {
        return ResponseEntity.ok(RestResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message(HttpStatus.OK.getReasonPhrase())
                        .data(googleService.getLoginPageUrl()).build());
    }

    @GetMapping("/oauth/callbacks/google")
    public ResponseEntity oAuth2Login(OAuth2CallbackDto callback) {
        // 회원가입 이후 닉네임 입력 어떻게 처리?

        if (callback.getCode() != null) {

            return ResponseEntity.ok(RestResponse.builder()
                            .status(HttpStatus.OK.value())
                            .message(HttpStatus.OK.getReasonPhrase())
                            .data(memberService.googleLogin(callback.getCode())).build());
        } else {

            return ResponseEntity.badRequest().body(callback.getErrorDescription());
        }
    }
}
