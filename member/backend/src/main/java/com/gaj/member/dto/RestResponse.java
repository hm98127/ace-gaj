package com.gaj.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RestResponse<T> {

    private int status;
    private String message;
    private Object data;
}
