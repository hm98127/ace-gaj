package com.gaj.member.domain;


import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
public class Member {

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
    private RoleType role;

    @Enumerated(EnumType.STRING)
    @Setter
    private StateType state;

    @CreatedDate
    private LocalDateTime createdDate;

    /* TODO
    * 회원탈퇴 -> member.setState("탈퇴");
    * 회원탈퇴 -> member.withdrawal();
    * */

}
