package com.urlshortner.urlshortner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.urlshortner.urlshortner.dto.UrlRequestDto;
import com.urlshortner.urlshortner.entity.Url;
import com.urlshortner.urlshortner.service.UrlService;

@RestController
@RequestMapping("/api/url")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<Url> shortenUrl(@RequestBody UrlRequestDto urlRequestDto) {
        Url url = urlService.shortenUrl(urlRequestDto.getDestinationUrl());
        return ResponseEntity.ok(url);
    }

    @GetMapping("/get")
    public ResponseEntity<String> getDestinationUrl(@RequestParam String shortUrl) {
        String value = shortUrl.substring(shortUrl.lastIndexOf('/') + 1);
        return urlService.getDestinationUrl(value)
                .map(url -> ResponseEntity.ok(url.getDestinationUrl()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateDestinationUrl(@RequestBody UrlRequestDto urlRequestDto) {
        boolean updated = urlService.updatedestinationUrl(urlRequestDto.getShortUrl(), urlRequestDto.getDestinationUrl());
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/update-expiry")
    public ResponseEntity<Boolean> updateExpiry(@RequestBody UrlRequestDto urlRequestDto) {
        boolean updated = urlService.updateExpiry(urlRequestDto.getShortUrl(), urlRequestDto.getDaysToAdd());
        return ResponseEntity.ok(updated);
    }
}
