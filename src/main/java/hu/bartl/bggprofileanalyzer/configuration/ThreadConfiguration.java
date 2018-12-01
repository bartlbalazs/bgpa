package hu.bartl.bggprofileanalyzer.configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadConfiguration {
    
    @Bean
    public ExecutorServiceFactory executorServiceFactory() {
        return () -> Executors.newCachedThreadPool();
    }
    
    public interface ExecutorServiceFactory {
        ExecutorService get();
    }
}
