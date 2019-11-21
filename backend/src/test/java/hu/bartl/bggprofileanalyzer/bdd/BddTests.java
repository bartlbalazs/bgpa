package hu.bartl.bggprofileanalyzer.bdd;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:bdd/features", plugin = {"pretty", "json:target/cucumber-report.json"})
public class BddTests {

}
