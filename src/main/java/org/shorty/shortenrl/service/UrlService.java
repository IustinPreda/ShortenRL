package org.shorty.shortenrl.service;


import org.shorty.shortenrl.entity.UrlEntity;
import org.shorty.shortenrl.repository.UrlRepository;
import org.shorty.shortenrl.util.Base62Encoder;
import org.springframework.stereotype.Service;

@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final Base62Encoder base62Encoder;

    public UrlService(UrlRepository urlRepository, Base62Encoder base62Encoder) {
        this.urlRepository = urlRepository;
        this.base62Encoder = base62Encoder;
    }

    public String shortenUrl(String originalUrl) {
        UrlEntity url = new UrlEntity(originalUrl);
        UrlEntity savedUrl = urlRepository.save(url);
        return base62Encoder.encode(savedUrl.getId());
    }

    public String getOriginalUrl(String shortCode) {
        long id = base62Encoder.decode(shortCode);
        return urlRepository.findById(id)
                .map(UrlEntity::getLongUrl)
                .orElseThrow(() -> new RuntimeException("URL not found for code: " + shortCode));
    }

}
