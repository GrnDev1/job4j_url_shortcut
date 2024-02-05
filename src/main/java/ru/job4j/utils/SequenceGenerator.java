package ru.job4j.utils;

import liquibase.repackaged.org.apache.commons.lang3.RandomStringUtils;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SequenceGenerator {
    private final PasswordEncoder passwordEncoder;

    public SequenceGenerator() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String generateSequence() {
        return RandomStringUtils.randomAlphanumeric(8);
    }

    public String getEncryptedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
