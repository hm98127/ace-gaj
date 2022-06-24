package com.gaj.member.exception;

public class MemberException extends RuntimeException {

    public static final MemberException MEMBER_NOT_EXISTS_EXCEPTION = new MemberException("사용자가 존재하지 않습니다.");
    public static final MemberException MEMBER_ALREADY_EXISTS_EXCEPTION = new MemberException("사용자가 이미 존재합니다.");

    public MemberException(String message) {
        super(message);
    }
}
