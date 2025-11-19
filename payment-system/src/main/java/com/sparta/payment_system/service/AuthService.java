package com.sparta.payment_system.service;

import com.sparta.payment_system.dto.auth.CreateUserRequest;
import com.sparta.payment_system.dto.auth.CreateUserResponse;
import com.sparta.payment_system.entity.User;
import com.sparta.payment_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        if(userRepository.existsByEmail(createUserRequest.getEmail())){
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        User user = new  User(
                createUserRequest.getEmail(),
                createUserRequest.getPasswordHash(),
                createUserRequest.getName()
        );

        userRepository.save(user);

        return new CreateUserResponse(
                user.getUserId(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getName(),
                user.getCreatedAt()
        );

    }

}
