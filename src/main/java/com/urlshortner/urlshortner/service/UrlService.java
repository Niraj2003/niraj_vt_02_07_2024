package com.urlshortner.urlshortner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.urlshortner.urlshortner.entity.Url;
import com.urlshortner.urlshortner.repository.UrlRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    public Url shortenUrl(String destinationUrl) {
        String shortUrl = generateShortUrl();
        LocalDateTime expiresAt = LocalDateTime.now().plusMonths(10);

        Url url = new Url();
        url.setShortUrl(shortUrl);
        url.setDestinationUrl(destinationUrl);
        url.setCreationDate(LocalDateTime.now());
        url.setExpiryDate(expiresAt);

        return urlRepository.save(url);
    }

    public Optional<Url> getDestinationUrl(String shortUrl) {
        return urlRepository.findByShortUrl(shortUrl);
    }

    @Transactional
    public boolean updatedestinationUrl(String shortUrl, String newdestinationUrl) {
        Optional<Url> optionalUrl = urlRepository.findByShortUrl(shortUrl);
        if (optionalUrl.isPresent()) {
            Url url = optionalUrl.get();
            url.setDestinationUrl(newdestinationUrl);
            urlRepository.save(url);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updateExpiry(String shortUrl, int daysToAdd) {
        Optional<Url> optionalUrl = urlRepository.findByShortUrl(shortUrl);
        if (optionalUrl.isPresent()) {
            Url url = optionalUrl.get();
            url.setExpiryDate(url.getExpiryDate().plusDays(daysToAdd));
            urlRepository.save(url);
            return true;
        }
        return false;
    }

    private String generateShortUrl() {
        // Implemention for unique short url
        return UUID.randomUUID().toString().substring(0, 8);
    } 
}
