package hu.bartl.bggprofileanalyzer.data;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class CacheEntry<T extends Serializable> implements Serializable {
    
    private final Instant insertedAt;
    private final T entry;
    
    public static <T extends Serializable> CacheEntry<T> of(T entry) {
        return new CacheEntry<>(Instant.now(), entry);
    }
}
