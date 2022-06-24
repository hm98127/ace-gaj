package com.gaj.member.dto.social;

import lombok.Data;

@Data
public class UserInfoResponseDto {

    private String id;
    private String email;
    private String verifiedEmail;
    private String name;
    private String givenName;
    private String familyName;
    private String picture;
    private String locale;
}
