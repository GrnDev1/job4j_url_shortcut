package ru.job4j.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlDto {
    @NotBlank(message = "Url must be not empty")
    @Pattern(regexp = "^https?://[a-zA-Z0-9.-]+\\.(ru|com)/.+", message = "Invalid Url")
    private String url;
    private Integer total;
}
