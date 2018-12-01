package hu.bartl.bggprofileanalyzer.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.bartl.bggprofileanalyzer.service.ProfileDownloader;

@RestController
@RequestMapping("/stats")
@Slf4j
public class StatController {
    
    @Autowired
    private ProfileDownloader profileDownloader;
    
    @RequestMapping("/{userId}")
    public String getStatsOfUser(@PathVariable String userId) {
        return profileDownloader.loadProfile(userId).toString();
    }
}
