package hu.bartl.bggprofileanalyzer.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.bartl.bggprofileanalyzer.data.UserStats;
import hu.bartl.bggprofileanalyzer.service.StatsService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/stats", produces = APPLICATION_JSON_VALUE)
@Slf4j
public class StatController {
    
    @Autowired
    private StatsService statsService;
    
    @RequestMapping("/{userId}")
    public UserStats getStatsOfUser(@PathVariable String userId) {
        return statsService.createStats(userId);
    }
}
