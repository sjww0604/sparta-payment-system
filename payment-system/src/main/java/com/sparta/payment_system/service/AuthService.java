package com.sparta.payment_system.service;

import com.sparta.payment_system.dto.auth.CreateUserRequest;
import com.sparta.payment_system.dto.auth.CreateUserResponse;
import com.sparta.payment_system.dto.auth.PostLoginRequest;
import com.sparta.payment_system.dto.auth.PostLoginResponse;
import com.sparta.payment_system.entity.User;
import com.sparta.payment_system.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 -> Bcrypt 사용안함
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        if(userRepository.existsByEmail(createUserRequest.getEmail())){
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        User user = new  User(
                createUserRequest.getEmail(),
                createUserRequest.getPasswordHash(),
                createUserRequest.getUserName()
        );

        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        userRepository.save(user);

        return new CreateUserResponse(
                user.getUserId(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getName(),
                user.getCreatedAt()
        );
    }

    // 로그인
    public PostLoginResponse userLogin(PostLoginRequest postLoginRequest) {

        User user = userRepository.findByEmail(postLoginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));

        // hash 사용하고 matches 메소드로 변경 예정
        if(!passwordEncoder.matches(postLoginRequest.getPassword(), user.getPasswordHash())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return new PostLoginResponse(
                user.getUserId(),
                user.getName()
        );
    }

}
