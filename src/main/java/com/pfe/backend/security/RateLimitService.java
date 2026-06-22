package com.pfe.backend.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private Bucket createBucket() {

        Bandwidth limit = Bandwidth.builder()
                .capacity(4)
                .refillIntervally(
                        4,
                        Duration.ofMinutes(1)
                )
                .build();

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    public Bucket resolveBucket(String ip) {
        return buckets.computeIfAbsent(ip, k -> createBucket());
    }
}