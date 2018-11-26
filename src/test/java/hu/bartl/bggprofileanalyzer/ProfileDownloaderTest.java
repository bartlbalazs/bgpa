package hu.bartl.bggprofileanalyzer;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.util.Arrays.asList;
import static org.assertj.core.util.Sets.newHashSet;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
public class ProfileDownloaderTest {
    
    public static final String SAMPLE_USER_PROFILE = "bgg_profile_bartlbalazs.xml";
    public static final String TEST_USER_ID = "testUserId";
    public static final Set<Integer> TEST_USER_GAMES =
            newHashSet(asList(39351, 182874, 111799, 45315, 178054, 181304, 38862, 187645, 172, 127023, 153737, 63628,
                              122842, 171499, 192458, 167513, 150376, 12333, 54998, 218804, 198994, 220308, 181279, 72225,
                              69779, 128721, 155821, 28723, 43528, 193738, 27833, 129050, 3076, 167791, 126163, 20963, 169786,
                              234948, 119337, 191051, 84876, 171668, 85325, 233398, 234487, 143515, 148949));
    
    @MockBean
    private RestTemplate restTemplate;
    
    @Autowired
    private EnvironmentInformations env;
    
    @Autowired
    public ProfileDownloader underTest;
    
    @Test
    public void loadProfile() {
        String profileUrl = env.getApiRoot() + "/collection/" + TEST_USER_ID;
        
        Mockito.when(restTemplate.getForEntity(profileUrl, String.class))
               .thenReturn(ResponseEntity.accepted().build(),
                           ResponseEntity.ok(getTestFileContent(SAMPLE_USER_PROFILE)));
        
        UserProfile profile = underTest.loadProfile(TEST_USER_ID);
        
        Assert.assertEquals("Profile ids are not equal!", TEST_USER_ID, profile.getUserId());
        Assert.assertEquals("Owned game ids are not equal!", TEST_USER_GAMES, profile.getOwnedGameIds());
    }
    
    @SneakyThrows
    private String getTestFileContent(String fileName) {
        Path path = Paths.get(getClass().getClassLoader().getResource(fileName).toURI());
        List<String> lines = Files.readAllLines(path);
        String data = lines.stream().collect(Collectors.joining());
        return data;
    }
}