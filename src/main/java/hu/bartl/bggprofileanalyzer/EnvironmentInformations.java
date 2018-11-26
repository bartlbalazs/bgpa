package hu.bartl.bggprofileanalyzer;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class EnvironmentInformations {
    
    @Value("${app.bgg-api-root}")
    private String apiRoot;
}
