package ru.job4j.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseRegistrationDto {
    private boolean registration;
    private String login;
    private String password;
}
