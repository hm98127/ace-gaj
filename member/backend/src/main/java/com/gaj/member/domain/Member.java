package com.gaj.member.domain;


import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
public class Member extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true)
    private String email;

    @Setter
    private String password;

    @Setter
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private RoleType role = RoleType.USER;

    @Enumerated(EnumType.STRING)
    @Setter
    @Builder.Default
    private StateType state = StateType.ACTIVE;

    public void withdrawal() {
        setState(StateType.WITHDRAWAL);
    }

    public boolean validPassword(PasswordEncoder passwordEncoder, String rawPassword) {
        return passwordEncoder.matches(rawPassword, password);
    }
}
