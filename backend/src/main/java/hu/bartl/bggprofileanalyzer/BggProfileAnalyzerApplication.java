package hu.bartl.bggprofileanalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableRetry
public class BggProfileAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BggProfileAnalyzerApplication.class, args);
	}
}
