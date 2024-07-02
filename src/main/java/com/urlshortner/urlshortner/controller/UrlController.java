package com.urlshortner.urlshortner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.urlshortner.urlshortner.entity.Url;
import com.urlshortner.urlshortner.service.UrlService;

@RestController
@RequestMapping("/api/url")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<Url> shortenUrl(@RequestParam String destinationUrl) {
        Url url = urlService.shortenUrl(destinationUrl);
        return ResponseEntity.ok(url);
    }

    @GetMapping("/get")
    public ResponseEntity<String> getDestinationUrl(@RequestParam String shortUrl) {
        return urlService.getDestinationUrl(shortUrl)
                .map(url -> ResponseEntity.ok(url.getDestinationUrl()))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateDestinationUrl(@RequestParam String shortUrl, @RequestParam String newDestinationUrl) {
        boolean updated = urlService.updatedestinationUrl(shortUrl, newDestinationUrl);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/update-expiry")
    public ResponseEntity<Boolean> updateExpiry(@RequestParam String shortUrl, @RequestParam int daysToAdd) {
        boolean updated = urlService.updateExpiry(shortUrl, daysToAdd);
        return ResponseEntity.ok(updated);
    }
}
