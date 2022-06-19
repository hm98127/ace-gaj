package com.gaj.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gaj.member.dto.social.TokenResponseDto;
import com.gaj.member.dto.social.UserInfoResponseDto;

public interface SocialServiceBase {

    public String getLoginPageUrl();
    public TokenResponseDto getTokenByCode(String code) throws JsonProcessingException;
    public UserInfoResponseDto getUserInfoByAccessToken(String accessCode) throws  JsonProcessingException;
}
