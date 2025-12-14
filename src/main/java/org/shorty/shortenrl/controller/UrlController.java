package org.shorty.shortenrl.controller;

import org.shorty.shortenrl.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }
    @PostMapping("/api/shorten")
    public ResponseEntity<String> shortenUrl(@RequestBody String originalUrl) {
        if (originalUrl == null || originalUrl.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Error: URL cannot be empty");
        }

        String shortCode = urlService.shortenUrl(originalUrl);
        String fullShortUrl = "http://localhost:8080/" + shortCode;
        return ResponseEntity.ok(fullShortUrl);
    }

    @GetMapping("/{shortCode:[a-zA-Z0-9]+}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = urlService.getOriginalUrl(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
