package hu.bartl.bggprofileanalyzer.bdd;

import hu.bartl.bggprofileanalyzer.BggProfileAnalyzerApplication;
import hu.bartl.bggprofileanalyzer.RedisTestConfiguration;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = BggProfileAnalyzerApplication.class, loader = SpringBootContextLoader.class)
@TestPropertySource(locations = "classpath:bdd/test.properties")
@Import({RedisTestConfiguration.class, MockServerConfig.class})
public class CucumberSpringContextConfiguration {


}