package hu.bartl.bggprofileanalyzer;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import javax.annotation.PostConstruct;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import hu.bartl.bggprofileanalyzer.data.BoardGame;

@RequiredArgsConstructor
@Component
public class BoardGameCache {
    
    private static final String KEY = "BoardGame";
    
    private final RedisTemplate<String, BoardGame> redisTemplate;
    private HashOperations hashOperations;
    
    @PostConstruct
    private void initialize() {
        hashOperations = redisTemplate.opsForHash();
    }
    
    public void put(BoardGame boardGame) {
        hashOperations.put(KEY, boardGame.getId(), boardGame);
    }
    
    public Optional<BoardGame> get(Integer id) {
        return Optional.ofNullable((BoardGame) hashOperations.get(KEY, id));
    }
}