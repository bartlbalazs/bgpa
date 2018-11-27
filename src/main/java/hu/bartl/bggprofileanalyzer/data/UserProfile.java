package hu.bartl.bggprofileanalyzer.data;

import lombok.Data;

import java.util.Set;

@Data
public class UserProfile {
    
    private String userId;
    private Set<BoardGame> boardGames;
}
