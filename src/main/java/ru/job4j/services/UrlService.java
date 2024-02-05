package ru.job4j.services;

import ru.job4j.dtos.FullUrlDto;
import ru.job4j.dtos.ShortUrlDto;
import ru.job4j.dtos.UrlDto;

import java.util.List;
import java.util.Optional;

public interface UrlService {
    List<UrlDto> findAll();

    ShortUrlDto convertUrl(UrlDto urlDto, String login);

    Optional<FullUrlDto> getFullUrl(String shortUrl);

}
