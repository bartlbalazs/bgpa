package hu.bartl.bggprofileanalyzer.bdd;

import org.mockserver.integration.ClientAndServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.annotation.PreDestroy;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;

@TestConfiguration
public class MockServerConfig {

    @Value("${app.bgg-api-root}")
    private String bggApiRoot;

    private ClientAndServer mockServer;

    @Bean
    public ClientAndServer mockServer() {
        Pattern p = Pattern.compile("([\\d+])\\w+");
        Matcher m = p.matcher(bggApiRoot);
        if (m.find()) {
            int mockServerPort = Integer.parseInt(m.group());
            mockServer = startClientAndServer(mockServerPort);
            return mockServer;
        }
        throw new RuntimeException("Port number cannot be extracted from configured BGG API root: " + bggApiRoot);
    }

    @PreDestroy
    public void stopMockServer() {
        if (mockServer != null) {
            mockServer.stop();
        }
    }
}
