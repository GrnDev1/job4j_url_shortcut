package ru.job4j.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.dtos.FullUrlDto;
import ru.job4j.dtos.ShortUrlDto;
import ru.job4j.dtos.UrlDto;
import ru.job4j.mappers.UrlMapper;
import ru.job4j.models.Site;
import ru.job4j.models.Url;
import ru.job4j.repositories.SiteRepository;
import ru.job4j.repositories.UrlRepository;
import ru.job4j.utils.SequenceGenerator;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleUrlService implements UrlService {

    private final UrlRepository urlRepository;
    private final SiteRepository siteRepository;
    private final UrlMapper urlMapper;
    private final SequenceGenerator sequenceGenerator;

    @Override
    public List<UrlDto> findAll() {
        return urlMapper.getListFromUrl(urlRepository.findAll());
    }

    @Override
    public ShortUrlDto convertUrl(UrlDto urlDto, String login) {
        Optional<Url> urlOptional = urlRepository.findByFullUrl(urlDto.getUrl());
        if (urlOptional.isPresent()) {
            return new ShortUrlDto(urlOptional.get().getShortUrl());
        }

        String shortUrl = sequenceGenerator.generateSequence();
        while (shortUrlExist(shortUrl)) {
            shortUrl = sequenceGenerator.generateSequence();
        }

        urlRepository.save(new Url(urlDto.getUrl(), shortUrl, getSite(login)));
        return new ShortUrlDto(shortUrl);
    }

    @Override
    public Optional<FullUrlDto> getFullUrl(String shortUrl) {
        Optional<Url> urlOptional = urlRepository.findByShortUrl(shortUrl);
        if (urlOptional.isPresent()) {
            urlRepository.incrementTotal(shortUrl);
            return Optional.of(new FullUrlDto(urlOptional.get().getFullUrl()));
        }
        return Optional.empty();
    }

    private boolean shortUrlExist(String shortUrl) {
        Optional<Url> urlOptional = urlRepository.findByShortUrl(shortUrl);
        return urlOptional.isPresent();
    }

    private Site getSite(String login) {
        return siteRepository.findByLogin(login).get();
    }

}
