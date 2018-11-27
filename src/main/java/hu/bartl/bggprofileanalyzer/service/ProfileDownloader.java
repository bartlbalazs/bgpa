package hu.bartl.bggprofileanalyzer.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.underscore.lodash.U;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import hu.bartl.bggprofileanalyzer.RetriableRemoteApiException;
import hu.bartl.bggprofileanalyzer.configuration.EnvironmentInformations;
import hu.bartl.bggprofileanalyzer.data.UserProfile;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileDownloader {
    
    private final String USER_PROFILE_API_ENDPOINT_PATTERN = "%s/collection/%s?own=1";
    private final BoardGameDownloader boardGameDownloader;
    private final EnvironmentInformations env;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    
    @Retryable(
            value = {RetriableRemoteApiException.class},
            maxAttempts = 10,
            backoff = @Backoff(delay = 5000, multiplier = 2))
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
        Set<Integer> ownedGameIds = extractGameIds(profileXml);
        userProfile.setBoardGames(boardGameDownloader.loadGames(ownedGameIds));
        return userProfile;
    }
    
    @SneakyThrows
    public Set<Integer> extractGameIds(String profileXml) {
        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {};
        Map<String, Object> profileMap = mapper.readValue(U.xmlToJson(profileXml), typeRef);
        Map<String, Object> items = (Map<String, Object>) profileMap.get("items");
        List<Map> item = (List<Map>) items.get("item");
        return item.stream()
                   .filter(i -> {
                       Map<String, String> status = (Map<String, String>) i.get("status");
                       return "1".equals(status.get("-own"));
                   })
                   .map(i -> Integer.valueOf((String) i.get("-objectid")))
                   .collect(Collectors.toSet());
    }
}
