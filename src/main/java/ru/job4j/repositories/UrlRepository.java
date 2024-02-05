package ru.job4j.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.models.Url;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepository extends CrudRepository<Url, Integer> {
    List<Url> findAll();

    Optional<Url> findByFullUrl(String fullUrl);

    Optional<Url> findByShortUrl(String shortUrl);

    @Transactional
    @Modifying
    @Query("UPDATE Url SET total = total + 1 WHERE shortUrl = :shortUrl")
    void incrementTotal(String shortUrl);
}
