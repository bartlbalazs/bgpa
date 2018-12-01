package hu.bartl.bggprofileanalyzer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;

import redis.embedded.RedisServer;

@TestConfiguration
public class RedisTestConfiguration {
    
    private final RedisServer redisServer;
    
    public RedisTestConfiguration(@Value("${spring.redis.port}") final int redisPort) {
        this.redisServer = new redis.embedded.RedisServer(redisPort);
    }
    
    @PostConstruct
    public void startRedis() {
        this.redisServer.start();
    }
    
    @PreDestroy
    public void stopRedis() {
        this.redisServer.stop();
    }
}
