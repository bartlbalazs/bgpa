package hu.bartl.bggprofileanalyzer.configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadConfig {
    
    @Bean
    public ExecutorServiceFactory executorServiceFactory() {
        return new ExecutorServiceFactory() {
            @Override
            public ExecutorService get() {
                return Executors.newCachedThreadPool();
            }
        };
    }
    
    public static interface ExecutorServiceFactory {
        
        ExecutorService get();
    }
}
