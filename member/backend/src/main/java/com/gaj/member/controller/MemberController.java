package com.gaj.member.controller;

import com.gaj.member.dto.LoginRequestDto;
import com.gaj.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1/api")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity login(LoginRequestDto dto) {

        memberService.login(dto);

        //return ResponseEntity.ok().build();
        return ResponseEntity.ok("유저 있음 O");
    }

    /* TODO
    * 회원가입, OAuth
    * */
}
