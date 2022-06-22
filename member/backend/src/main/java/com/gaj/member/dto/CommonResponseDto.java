package com.gaj.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommonResponseDto<T> {

    private String statusCode;
    private T data;

    @Builder
    public CommonResponseDto(Integer statusCode, T data) {
        this.statusCode = statusCode.toString();
        this.data = data;
    }
}
