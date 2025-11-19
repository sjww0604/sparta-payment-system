package com.sparta.payment_system.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostLoginResponse {
    private Long userId;
    private String userName;

    // 시큐리티 추가시 토큰 발행
    //private String accessToken;
    //private String refreshToken;


    public PostLoginResponse(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
