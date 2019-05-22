package hu.bartl.bggprofileanalyzer.service;

import hu.bartl.bggprofileanalyzer.configuration.EnvironmentInformations;
import hu.bartl.bggprofileanalyzer.data.user.UserProfile;
import hu.bartl.bggprofileanalyzer.exception.RetriableRemoteApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileDownloader {
    
    private final String USER_PROFILE_API_ENDPOINT_PATTERN = "%s/collection/%s?own=1";
    private final BoardGameDownloader boardGameDownloader;
    private final EnvironmentInformations env;
    private final RestTemplate restTemplate;
    private final XmlParser xmlParser;
    
    @Retryable(
            value = {RetriableRemoteApiException.class},
            maxAttempts = 10,
            backoff = @Backoff(delay = 1000, multiplier = 2))
    public UserProfile loadProfile(String userId) {
        log.info("Downloading information about user started: '{}'", userId);
        String profileUrl = String.format(USER_PROFILE_API_ENDPOINT_PATTERN, env.getApiRoot(), userId);
        ResponseEntity<String> profileEntity = restTemplate.getForEntity(profileUrl, String.class);
        if (profileEntity.getStatusCode() == HttpStatus.ACCEPTED) {
            log.info("The remote API is not ready to serve profile information for user '{}'", userId);
            throw new RetriableRemoteApiException();
        }
        log.info("Profile for user '{}' was downloaded successfully.", userId);
        String profileXml = profileEntity.getBody();
        
        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(userId);
        Set<Integer> ownedGameIds = xmlParser.extractGameIds(profileXml);
        userProfile.setBoardGames(boardGameDownloader.loadGames(ownedGameIds));
        return userProfile;
    }
    
}
