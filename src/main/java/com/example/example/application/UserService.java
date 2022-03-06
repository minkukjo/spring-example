package com.example.example.application;

import com.example.example.domain.User;
import com.example.example.infrastructure.projection.UserAndOrder;
import com.example.example.infrastructure.repository.UserRepository;
import com.example.example.presentation.api.request.SignupRequest;
import com.example.example.presentation.exception.NotFoundUserException;
import com.example.example.utils.PhoneNumberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User signup(SignupRequest signupRequest) {
        final User user = User.builder()
                .email(signupRequest.getEmail())
                .name(signupRequest.getName())
                .nickname(signupRequest.getNickname())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .phoneNumber(PhoneNumberUtil.toNumber(signupRequest.getPhoneNumber()))
                .gender(signupRequest.getGender())
                .build();
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(NotFoundUserException::new);
    }

    @Transactional(readOnly = true)
    public Page<UserAndOrder> findLastOrderPerUser(Pageable pageable) {
        return userRepository.findLastOrderPerUser(pageable);
    }

    @Transactional(readOnly = true)
    public Page<UserAndOrder> findLastOrderPerUserByEmail(String email, Pageable pageable) {
        return userRepository.findLastOrderPerUserByEmail(email, pageable);
    }

    @Transactional(readOnly = true)
    public Page<UserAndOrder> findLastOrderPerUserByName(String name, Pageable pageable) {
        return userRepository.findLastOrderPerUserByName(name, pageable);
    }

}
