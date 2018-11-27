package hu.bartl.bggprofileanalyzer.configuration;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class EnvironmentInformations {
    
    @Value("${app.bgg-api-root}")
    private String apiRoot;
    
    @Value("${app.games-per-api-request}")
    private int gamesPerApiRequest;
}
