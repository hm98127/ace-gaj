package com.gaj.member.dto.social;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuth2CallbackDto {

    private String code;
    private String error;
    private String errorDescription;
}
