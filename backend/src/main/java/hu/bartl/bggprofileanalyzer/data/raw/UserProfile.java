package hu.bartl.bggprofileanalyzer.data.raw;

import hu.bartl.bggprofileanalyzer.data.BoardGame;
import lombok.Data;

import java.util.Set;

@Data
public class UserProfile {
    
    private String userId;
    private Set<BoardGame> boardGames;
}
