package com.Sergio.urlShortener.entities;

import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "url")
public class Url {

    @Id
    private String originalUrl;
    private String shortUrl;

    public Url(String originalUrl) {
        this.originalUrl = originalUrl;
        this.shortUrl = DigestUtils.md2Hex(originalUrl);
    }

    public Url() {
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
