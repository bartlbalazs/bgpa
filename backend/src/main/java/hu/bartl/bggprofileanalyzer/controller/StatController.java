package hu.bartl.bggprofileanalyzer.controller;

import hu.bartl.bggprofileanalyzer.data.user.UserStatsPresentation;
import hu.bartl.bggprofileanalyzer.service.PresentationService;
import hu.bartl.bggprofileanalyzer.service.StatsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/stats", produces = APPLICATION_JSON_VALUE)
@Slf4j
public class StatController {

    @Autowired
    private StatsService statsService;

    @Autowired
    private PresentationService presentationService;

    @RequestMapping("/{userId}")
    public UserStatsPresentation getStatsOfUser(@PathVariable String userId) {
        StopWatch watch = new StopWatch();
        watch.start();
        log.info("Stats requested for user: {}", userId);
        UserStatsPresentation stats = presentationService.rawStatsToPresentation(statsService.createStats(userId));
        watch.stop();
        log.info("Stats for user {} calculated in {} seconds", userId, watch.getTotalTimeSeconds());
        return stats;
    }
}
