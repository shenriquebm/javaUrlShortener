package com.Sergio.urlShortener;

import com.Sergio.urlShortener.cache.ShortUrlCache;
import com.Sergio.urlShortener.entities.Url;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ShortUrlCacheTest {

    @Test
    public void shouldCacheUrls() {
        String urlAddr = "http://www.google.com";
        ShortUrlCache.getInstance().put(urlAddr, new Url(urlAddr));

        assertEquals(urlAddr, ShortUrlCache.getInstance().get(urlAddr).getOriginalUrl());
    }

    @Test
    public void shouldNotCacheMoreThanMaximum() {
        String urlAddr = "http://www.google.com/";
        for (int i = 0 ; i < 151 ; i++) {
            String addr = urlAddr + i;
            ShortUrlCache.getInstance().put(addr, new Url(addr));
        }

        assertFalse(ShortUrlCache.getInstance().containsKey(urlAddr + "0"));
    }
}
