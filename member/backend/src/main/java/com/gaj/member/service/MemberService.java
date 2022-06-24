package com.gaj.member.service;

import com.gaj.member.domain.Member;
import com.gaj.member.dto.social.TokenResponseDto;
import com.gaj.member.dto.social.UserInfoResponseDto;
import com.gaj.member.exception.MemberException;
import com.gaj.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
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

    private int TEMP_PASSWORD_SIZE = 12;

    public String login(Member member) throws MemberException {

        Optional<Member> targetMember = memberRepository.findByEmail(member.getEmail());

        if (targetMember.isPresent() && targetMember.get().validPassword(passwordEncoder, member.getPassword())) {
            return "{LOGGED_IN_JWT}";
        } else {
            throw MemberException.MEMBER_NOT_EXISTS_EXCEPTION;
        }
    }

    public String signup(Member member) {

        Optional<Member> targetMember = memberRepository.findByEmail(member.getEmail());

        if (targetMember.isEmpty()) {
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            memberRepository.save(member);
            return "{SIGNED_UP_JWT}";
        } else {
            throw MemberException.MEMBER_ALREADY_EXISTS_EXCEPTION;
        }
    }


    public String socialLogin(Member member) {

        Optional<Member> targetMember = memberRepository.findByEmail(member.getEmail());

        if (targetMember.isPresent()) {

            return "{SOCIAL_LOGGED_IN_JWT}";
        } else {

            throw MemberException.MEMBER_NOT_EXISTS_EXCEPTION;
        }
    }

    public String socialSignup(Member member) {

        String tempPassword = RandomString.make(TEMP_PASSWORD_SIZE);
        member.setPassword(passwordEncoder.encode(tempPassword));
        memberRepository.save(member);
        return "{SOCIAL_SIGNED_UP_JWT}";
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
