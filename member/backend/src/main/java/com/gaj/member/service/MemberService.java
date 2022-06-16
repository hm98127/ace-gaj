package com.gaj.member.service;

import com.gaj.member.domain.Member;
import com.gaj.member.dto.LoginRequestDto;
import com.gaj.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public void login(LoginRequestDto dto) {
        // OAuth password(x) email, oauth_id, oauth_provider ??

        Optional<Member> member = memberRepository.findByEmail(dto.getEmail());
        if (member.isEmpty()) {
            // exception
            System.out.println("유저 없음 X");
        }
        System.out.println("유저 있음 O");
    }
}
