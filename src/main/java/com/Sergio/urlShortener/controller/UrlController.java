package com.Sergio.urlShortener.controller;

import com.Sergio.urlShortener.cache.ShortUrlCache;
import com.Sergio.urlShortener.cache.UrlCache;
import com.Sergio.urlShortener.entities.Url;
import com.Sergio.urlShortener.repository.UrlRepository;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
public class UrlController {

    private final UrlRepository urlRepository;
    @Autowired
    public UrlController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    /**
     * receives a new url and then handles the request accordingly.
     * Checks whether the URL is in cache for faster response times. If it's not on cache, check on the persistence
     * layer.
     *
     * If the URL is found in the persistence, the cache is populated. If the URL is not found on the cache and on the
     * persistence, it is created in both.
     * @return Response with the Object created and status code
     */
    @PostMapping(path = "/")
    public ResponseEntity receiveUrl(@RequestBody String link) {
        if (UrlValidator.getInstance().isValid(link)) {
            if (!UrlCache.getInstance().containsKey(link)) {
                Optional<Url> urlDatabaseEntry = urlRepository.findById(link);
                Url url;
                if (urlDatabaseEntry.isPresent()) {
                    url = urlDatabaseEntry.get();
                }
                else {
                    url = new Url(link);
                    urlRepository.save(url);
                }
                UrlCache.getInstance().put(url);
                return new ResponseEntity(UrlCache.getInstance().get(link), HttpStatus.OK);
            }
            else {
                return new ResponseEntity(UrlCache.getInstance().get(link), HttpStatus.OK);
            }
        }
        else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Checks whether the shortened URL exists, if so, issues a redirect. Otherwise returns a NOT FOUND
     * @return
     */
    @GetMapping(path = "/{short_url}")
    public ResponseEntity retrieveUrl(@PathVariable(value = "short_url") String shortUrl, HttpServletResponse response) throws IOException {
        if (!ShortUrlCache.getInstance().containsKey(shortUrl)) {
            Optional<Url> urlDatabaseEntry = urlRepository.findByShortUrl(shortUrl);
            if (!urlDatabaseEntry.isPresent()) {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
            else {
                ShortUrlCache.getInstance().put(urlDatabaseEntry.get());
                response.sendRedirect(ShortUrlCache.getInstance().get(shortUrl).getOriginalUrl());
                return null;
            }
        }
        else {
            response.sendRedirect(ShortUrlCache.getInstance().get(shortUrl).getOriginalUrl());
            return null;
        }
    }
}
