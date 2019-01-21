package com.Sergio.urlShortener.cache;

import com.Sergio.urlShortener.entities.Url;
import org.apache.commons.collections4.map.LRUMap;

public class UrlCache extends LRUMap<String, Url> {
    private static UrlCache ourInstance = new UrlCache();

    public static UrlCache getInstance() {
        return ourInstance;
    }

    /**
     * Simulating a small server. In practice a much larger number could be used.
     */
    private UrlCache() {
        super(150);
    }

    /**
     * Helper method to include a URL,
     * @param url {@link Url} entity to be saved in memory
     * @return the Url
     */
    public Url put(Url url) {
        super.put(url.getOriginalUrl(), url);
        return url;
    }
}
