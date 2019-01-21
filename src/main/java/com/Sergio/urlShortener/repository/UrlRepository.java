package com.Sergio.urlShortener.repository;

import com.Sergio.urlShortener.entities.Url;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UrlRepository extends CrudRepository<Url, String> {
    public Optional<Url> findByShortUrl(String shortUrl);
}
