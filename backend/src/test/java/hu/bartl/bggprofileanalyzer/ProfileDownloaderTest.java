package hu.bartl.bggprofileanalyzer;

import hu.bartl.bggprofileanalyzer.configuration.EnvironmentInformations;
import hu.bartl.bggprofileanalyzer.data.user.UserProfile;
import hu.bartl.bggprofileanalyzer.service.ProfileDownloader;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static hu.bartl.bggprofileanalyzer.TestHelper.getTestFileContent;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
@Import(RedisTestConfiguration.class)
public class ProfileDownloaderTest {
    
    public static final String TEST_USER_ID = "testUserId";
    
    @MockBean
    private RestTemplate restTemplate;
    
    @Autowired
    private EnvironmentInformations env;
    
    @Autowired
    public ProfileDownloader underTest;
    
    @Test
    public void loadProfile() {
        String profileUrl = env.getApiRoot() + "/collection/" + TEST_USER_ID + "?own=1";
        
        Mockito.when(restTemplate.getForEntity(profileUrl, String.class))
               .thenReturn(ResponseEntity.accepted().build(),
                           ResponseEntity.ok(getTestFileContent(TestHelper.SAMPLE_USER_COLLECTION)));
        
        Mockito.when(restTemplate.getForEntity(startsWith(env.getApiRoot() + "/boardgame/"), any(Class.class)))
               .thenReturn(ResponseEntity.ok(getTestFileContent(TestHelper.SAMPLE_USER_GAMES_DETAILS)));
        
        UserProfile profile = underTest.loadProfile(TEST_USER_ID);
        
        Assert.assertEquals("Profile ids are not equal!", TEST_USER_ID, profile.getUserId());
        Assert.assertEquals("Count of downloaded games is not correct!", 3, profile.getBoardGames().size());
    }
}