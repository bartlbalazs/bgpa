package hu.bartl.bggprofileanalyzer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

import hu.bartl.bggprofileanalyzer.data.BoardGame;

@Configuration
public class RedisConfiguration {
//
//    public @Bean
//    RedisConnectionFactory connectionFactory() {
//
//        return new JedisConnectionFactory(
//                new RedisClusterConfiguration(clusterProperties.getNodes()));
//    }

    @Bean
    public RedisTemplate<String, BoardGame> redisTemplate(RedisConnectionFactory connectionFactory) {
        final RedisTemplate<String, BoardGame> template = new RedisTemplate<String, BoardGame>();
        template.setConnectionFactory(connectionFactory);
        template.setValueSerializer(new GenericToStringSerializer<BoardGame>(BoardGame.class));
        return template;
    }
}
