package com.gaj.member.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.gaj.member.dto.social.TokenRequestDto;
import com.gaj.member.dto.social.TokenResponseDto;
import com.gaj.member.dto.social.UserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Service
public class GoogleService implements SocialServiceBase{

    @Value("${google-oauth-url}")
    private String oAuthUrl;

    @Value("${google-redirect-uri}")
    private String redirectUri;

    @Value("${google-client-id}")
    private String clientId;

    @Value("${google-client-secret}")
    private String clientSecret;

    @Value("${google-token-url}")
    private String tokenUrl;

    @Value("${google-profile-url}")
    private String profileUrl;

    public String getLoginPageUrl() {

        return oAuthUrl
                .concat("?scope=").concat("https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile")
                .concat("&access_type=").concat("offline")
                .concat("&include_granted_scopes=").concat("true")
                .concat("&response_type=").concat("code")
                .concat("state=").concat("")
                .concat("redirect_uri=").concat(redirectUri)
                .concat("client_id=").concat(clientId);
    }

    public TokenResponseDto getTokenByCode(String code) {

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        TokenRequestDto oAuth2RequestDto = TokenRequestDto.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .code(code)
                .redirectUri(redirectUri)
                .grantType("authorization_code")
                .build();

        ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, oAuth2RequestDto, String.class);

        try {
            TokenResponseDto tokenResponseDto = objectMapper.readValue(response.getBody(), new TypeReference<TokenResponseDto>() {});
            return tokenResponseDto;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserInfoResponseDto getUserInfoByAccessToken(String accessToken) {

        HttpHeaders httpHeaders = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        httpHeaders.set("Authorization", "Bearer " + accessToken);
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(profileUrl, HttpMethod.GET, request, String.class);

        try {
            UserInfoResponseDto userInfoResponseDto = objectMapper.readValue(response.getBody(), new TypeReference<UserInfoResponseDto>() {});
            return userInfoResponseDto;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
