package com.Sergio.urlShortener.cache;

import com.Sergio.urlShortener.entities.Url;
import org.apache.commons.collections4.map.LRUMap;

/**
 * Keeps the most recently used URLs in memory, in order to reduce persistence layer access and a lower p90 latency
 */
public class ShortUrlCache extends LRUMap<String, Url> {
    private static ShortUrlCache ourInstance = new ShortUrlCache();

    public static ShortUrlCache getInstance() {
        return ourInstance;
    }

    /**
     * Simulating a small server. In practice a much larger number could be used.
     */
    private ShortUrlCache() {
        super(150);
    }

    /**
     * Helper method to include a URL, calculate it's hash and then use the hash as the key to the map
     * @param url {@link Url} entity to be saved in memory
     * @return the Url
     */
    public Url put(Url url) {
        super.put(url.getShortUrl(), url);
        return url;
    }
}
