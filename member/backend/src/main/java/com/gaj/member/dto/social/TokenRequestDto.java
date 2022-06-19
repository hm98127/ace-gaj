package com.gaj.member.dto.social;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenRequestDto {

    private String clientId;
    private String clientSecret;
    private String code;
    private String redirectUri;
    private String grantType;
}
