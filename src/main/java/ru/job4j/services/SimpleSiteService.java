package ru.job4j.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.dtos.RequestRegistrationDto;
import ru.job4j.dtos.ResponseRegistrationDto;
import ru.job4j.models.Site;
import ru.job4j.repositories.SiteRepository;
import ru.job4j.utils.SequenceGenerator;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class SimpleSiteService implements SiteService {
    private final SiteRepository siteRepository;
    private final SequenceGenerator generator;

    public ResponseRegistrationDto save(RequestRegistrationDto dto) {
        String login = dto.getSite();
        ResponseRegistrationDto responseRegistrationDto = new ResponseRegistrationDto();
        responseRegistrationDto.setLogin(login);

        Optional<Site> siteOptional = siteRepository.findByLogin(login);
        if (siteOptional.isPresent()) {
            log.error(String.format("Site with domain: '%s' already exists", login));
            return responseRegistrationDto;
        }

        String password = generator.generateSequence();
        responseRegistrationDto.setPassword(password);
        responseRegistrationDto.setRegistration(true);

        siteRepository.save(new Site(login, generator.getEncryptedPassword(password)));
        return responseRegistrationDto;
    }
}
