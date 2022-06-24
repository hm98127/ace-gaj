package com.gaj.member.dto.social;

import lombok.Data;

@Data
public class TokenResponseDto {

    private String accessToken;
    private String expiresIn;
    private String tokenType;
    private String scope;
    private String refreshToken;
    private String idToken;
}
