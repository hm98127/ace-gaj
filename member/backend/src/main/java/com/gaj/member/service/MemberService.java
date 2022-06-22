package com.gaj.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gaj.member.domain.Member;
import com.gaj.member.dto.social.TokenResponseDto;
import com.gaj.member.dto.social.UserInfoResponseDto;
import com.gaj.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final GoogleService googleService;

    public void login() {

        // return JWT
    }

    public void signup(Member member) {

        // memberRepository.save(member);
        // return JWT
    }


    public ResponseEntity socialLogin(Member member) {

        // return JWT
        return ResponseEntity.ok("Logged in");
    }

    public ResponseEntity socialSignup(Member member) {

        memberRepository.save(member);
        // return JWT
        return ResponseEntity.ok("Signed up");
    }

    @Transactional
    public ResponseEntity googleLogin(String code) {

        TokenResponseDto tokenResponseDto = googleService.getTokenByCode(code);
        UserInfoResponseDto userInfoResponseDto = googleService.getUserInfoByAccessToken(tokenResponseDto.getAccessToken());

        Optional<Member> member = memberRepository.findByEmail(userInfoResponseDto.getEmail());

        if (member.isPresent()) {
            // login
            return socialLogin(member.get());
        } else {
            // signup
            return socialSignup(Member.builder()
                            .email(userInfoResponseDto.getEmail())
                            .build());
        }
    }
}
