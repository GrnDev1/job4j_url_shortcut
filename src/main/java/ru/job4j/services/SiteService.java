package ru.job4j.services;

import ru.job4j.dtos.RequestRegistrationDto;
import ru.job4j.dtos.ResponseRegistrationDto;

public interface SiteService {
    ResponseRegistrationDto save(RequestRegistrationDto dto);
}
