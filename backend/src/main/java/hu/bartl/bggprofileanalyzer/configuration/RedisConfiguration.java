package hu.bartl.bggprofileanalyzer.configuration;

import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.CacheEntry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String, CacheEntry<BoardGame>> redisTemplate(RedisConnectionFactory connectionFactory) {
        final RedisTemplate<String, CacheEntry<BoardGame>> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setValueSerializer(new GenericToStringSerializer<>(CacheEntry.class));
        return template;
    }
}
