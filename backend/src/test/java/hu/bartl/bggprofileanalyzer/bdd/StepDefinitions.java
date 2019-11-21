package hu.bartl.bggprofileanalyzer.bdd;

import com.google.common.collect.Maps;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import org.mockserver.integration.ClientAndServer;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.stream.Collectors;

import static hu.bartl.bggprofileanalyzer.TestHelper.getTestFileContent;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;


public class StepDefinitions extends CucumberSpringContextConfiguration {

    @Autowired
    private ClientAndServer mockServer;

    @Autowired
    private Environment env;

    @Value("${server.port}")
    private Integer serverPort;

    private final Map<String, Object> CONTEXT = Maps.newHashMap();

    @Given("{string} has a collection of games described in: {string}")
    public void testuserHasACollectionOfGamesDescribedInTestUserCollectionXml(String user, String collectionFileName) {
        String testFileContent = getTestFileContent("bdd/sample_data/bgg/"
                + collectionFileName);

        CONTEXT.put("testUserCollection", testFileContent);

    }


    @Given("BGG XML API can provide details for games:")
    public void bggXMLAPICanProvideDetailsForGames(DataTable gamesTable) {
        String startTag = "<boardgames termsofuse=\"https://boardgamegeek.com/xmlapi/termsofuse\">";
        String closeTag = "</boardgames>";

        String ids = gamesTable.asMaps().stream().map(g -> g.get("id")).collect(Collectors.joining(","));

        String descriptions = gamesTable.asMaps()
                .stream()
                .map(g -> g.get("details file"))
                .map(f -> getTestFileContent("bdd/sample_data/bgg/" + f))
                .collect(Collectors.joining());


        mockServer.when(request()
                .withMethod("GET")
                .withPath("/xmlapi/boardgame/" + ids)
                .withQueryStringParameter("stats", "1"))
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody(startTag + descriptions + closeTag)
                );
    }

    @Given("BGG XML API is ready to serve profile information for user {string}")
    public void bggXMLAPIIsReadyToServeProfileInformationForUserTestUser(String user) {
        String testFileContent = (String) CONTEXT.get("testUserCollection");
        mockServer.when(request()
                .withMethod("GET")
                .withPath("/xmlapi/collection/" + user)
                .withQueryStringParameter("own", "1"))
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody(testFileContent)
                );
    }

    @Given("BGG XML API can provide information about user {string} after {int} tries")
    public void bggXMLAPICanProvideInformationAboutUserTestUserAfterTries(String user, int tries) {
        String testFileContent = (String) CONTEXT.get("testUserCollection");

        mockServer.when(request()
                        .withMethod("GET")
                        .withPath("/xmlapi/collection/" + user)
                        .withQueryStringParameter("own", "1"),
                exactly(tries))
                .respond(response().withStatusCode(202));

        mockServer.when(request()
                .withMethod("GET")
                .withPath("/xmlapi/collection/" + user)
                .withQueryStringParameter("own", "1"))
                .respond(
                        response()
                                .withStatusCode(200)
                                .withBody(testFileContent)
                );
    }

    @When("profile information is requested for {string}")
    public void profileInformationIsRequestedForTestUser(String user) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://localhost:" + serverPort + "/api/stats/" + user;
        String result = restTemplate.getForEntity(apiUrl, String.class).getBody();
        CONTEXT.put("result", result);
    }

    @Then("the result is {string}")
    @SneakyThrows
    public void theResultIsTestUserJson(String resultFileName) {
        String result = (String) CONTEXT.get("result");
        JSONAssert.assertEquals(getTestFileContent("bdd/sample_data/bgpa/" + resultFileName), result, false);
    }
}
