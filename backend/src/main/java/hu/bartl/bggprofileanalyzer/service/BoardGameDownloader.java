package hu.bartl.bggprofileanalyzer.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import hu.bartl.bggprofileanalyzer.configuration.EnvironmentInformations;
import hu.bartl.bggprofileanalyzer.configuration.ThreadConfiguration.ExecutorServiceFactory;
import hu.bartl.bggprofileanalyzer.data.BoardGame;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardGameDownloader {
    
    private static final String BOARDGAME_API_ENDPOINT_PATTERN = "%s/boardgame/%s?stats=1";
    
    private final BoardGameCache boardGameCache;
    private final XmlParser xmlParser;
    private final EnvironmentInformations env;
    private final RestTemplate restTemplate;
    private final ExecutorServiceFactory executorServiceFactory;
    
    @SneakyThrows
    public Set<BoardGame> loadGames(Set<Integer> gameIds) {
        ExecutorService executorService = executorServiceFactory.get();
        
        List<Integer> idList = new ArrayList<>(gameIds);
        Set<BoardGame> result = new HashSet<>();
        
        int pageSize = env.getGamesPerApiRequest();
        int idPointer = 0;
        while (idPointer < idList.size()) {
            int firstIdIndex = idPointer;
            executorService.execute(() -> {
                result.addAll(loadGames(idList, firstIdIndex, pageSize));
            });
            idPointer += pageSize;
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        return result;
    }
    
    private Set<BoardGame> loadGames(List<Integer> idList, int firstIdIndex, int pageSize) {
        Set<Integer> idsInPage = idList.stream().skip(firstIdIndex).limit(pageSize).collect(Collectors.toSet());
       
        Set<BoardGame> result = boardGameCache.getAll(idsInPage);
        Set<Integer> cachedGameIds = result.stream().map(BoardGame::getId).collect(Collectors.toSet());
        idsInPage.removeAll(cachedGameIds);
        
        Set<BoardGame> downloadedGames = downloadGames(idsInPage);
        result.addAll(downloadedGames);
        
        return result;
    }
    
    private Set<BoardGame> downloadGames(Set<Integer> idsToDownload) {
        Set<BoardGame> result = new HashSet<>();
        if (idsToDownload.isEmpty()) {
            log.debug("Nothing to download.");
            return result;
        }
        
        String idsParameter = idsToDownload.stream().map(String::valueOf).collect(Collectors.joining(","));
        String boardgamesUrl = String.format(BOARDGAME_API_ENDPOINT_PATTERN, env.getApiRoot(), idsParameter);
        
        try {
            ResponseEntity<String> boardgamesXml = restTemplate.getForEntity(boardgamesUrl, String.class);
            Set<BoardGame> downloadedGames = xmlParser.parseBoardGames(boardgamesXml.getBody());
            log.info("Games downloaded: {}", downloadedGames);
            downloadedGames.forEach(boardGameCache::put);
            result = downloadedGames;
        } catch (ResourceAccessException | HttpClientErrorException e) {
            log.error("Failed to download games from URL: '{}'", boardgamesUrl, e);
        }
        return result;
    }
}