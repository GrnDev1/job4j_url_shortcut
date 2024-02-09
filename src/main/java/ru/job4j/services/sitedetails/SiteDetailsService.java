package ru.job4j.services.sitedetails;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.models.Site;
import ru.job4j.repositories.SiteRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class SiteDetailsService implements UserDetailsService {
    private final SiteRepository siteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Site site = siteRepository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Site '%s' not found", username)
        ));
        return new User(site.getLogin(), site.getPassword(), List.of());
    }
}
