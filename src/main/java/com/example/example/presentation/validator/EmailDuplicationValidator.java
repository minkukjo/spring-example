package com.example.example.presentation.validator;

import com.example.example.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
@RequiredArgsConstructor
public class EmailDuplicationValidator implements ConstraintValidator<EmailUnique, String> {

    private final UserRepository userRepository;

    private EmailUnique emailUnique;

    @Override
    public void initialize(EmailUnique emailUnique) {
        this.emailUnique = emailUnique;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext cxt) {
        boolean isExistEmail = userRepository.existsByEmail(email);
        if (isExistEmail) {
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate(
                            emailUnique.message())
                    .addConstraintViolation();
        }
        return !isExistEmail;
    }
}