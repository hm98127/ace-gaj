package com.gaj.member.service;

import com.gaj.member.domain.Member;
import com.gaj.member.dto.social.TokenResponseDto;
import com.gaj.member.dto.social.UserInfoResponseDto;
import com.gaj.member.exception.MemberException;
import com.gaj.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final GoogleService googleService;

    public String login(Member member) throws MemberException {
        Optional<Member> targetMember = memberRepository.findByEmail(member.getEmail());

        if (targetMember.isPresent() &&
                targetMember.get().validPassword(passwordEncoder, member.getPassword())) {
            return "{JWT}";
        } else {
            throw MemberException.MEMBER_NOT_EXISTS_EXCEPTION;
        }
    }

    public String signup(Member member) {
        Optional<Member> targetMember = memberRepository.findByEmail(member.getEmail());

        if (targetMember.isEmpty()) {
            memberRepository.save(member);
            return "{JWT}";
        } else {
            throw MemberException.MEMBER_ALREADY_EXISTS_EXCEPTION;
        }
    }


    public String socialLogin(Member member) {

        // return JWT
        return "{JWT}";
    }

    public String socialSignup(Member member) {

        memberRepository.save(member);
        // return JWT
        return "{JWT}";
    }

    // JWT return
    @Transactional
    public String googleLogin(String code) {

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
