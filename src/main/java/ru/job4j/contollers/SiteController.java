package ru.job4j.contollers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dtos.FullUrlDto;
import ru.job4j.dtos.RequestRegistrationDto;
import ru.job4j.dtos.ResponseRegistrationDto;
import ru.job4j.dtos.UrlDto;
import ru.job4j.services.site.SiteService;
import ru.job4j.services.url.UrlService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.Optional;

@RestController
@Controller
@AllArgsConstructor
public class SiteController {
    private final SiteService siteService;
    private final UrlService urlService;

    @PostMapping("/registration")
    public ResponseEntity<ResponseRegistrationDto> registration(@Valid @RequestBody RequestRegistrationDto dto) {
        return new ResponseEntity<>(siteService.save(dto), HttpStatus.OK);

    }

    @PostMapping("/convert")
    public ResponseEntity<?> convert(@Valid @RequestBody UrlDto urlDto, Principal principal) {
        String principalName = principal.getName();
        String urlDomain = getDomainFromUrl(urlDto.getUrl());

        if (!principalName.equals(urlDomain)) {
            return new ResponseEntity<>(
                    String.format("Validation error. Site domain: '%s' not equal current Url domain: '%s'",
                            principalName, urlDomain), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(urlService.convertUrl(urlDto, principalName), HttpStatus.OK);
    }

    private String getDomainFromUrl(String url) {
        return url.split("//")[1].split("/")[0];
    }

    @GetMapping("/redirect/{shortUrl}")
    public ResponseEntity<?> redirectOnUrl(@PathVariable String shortUrl) {
        Optional<FullUrlDto> urlOptional = urlService.getFullUrl(shortUrl);
        if (urlOptional.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", urlOptional.get().getFullUrl());
            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
        return new ResponseEntity<>("This short Url was not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/statistic")
    public ResponseEntity<Collection<UrlDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(urlService.findAll());
    }
}