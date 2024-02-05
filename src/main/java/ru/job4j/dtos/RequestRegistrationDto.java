package ru.job4j.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class RequestRegistrationDto {
    @NotBlank(message = "Site must be not empty")
    @Pattern(regexp = "^[a-zA-Z0-9.-]+\\.(ru|com)$", message = "Invalid domain name")
    private String site;
}
