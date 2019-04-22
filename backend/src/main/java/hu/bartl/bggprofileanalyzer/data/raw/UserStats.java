package hu.bartl.bggprofileanalyzer.data.raw;

import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.Popularity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class UserStats {
    
    private final String userId;
    private final Set<BoardGame> games;
    
    private final List<Popularity> categoryPopularities;
    private final List<Popularity> mechanismPopularities;
    private final List<Popularity> designerPopularities;
    private final List<Popularity> subDomainPopularities;
    private final List<Popularity> artistPopularities;
    private final List<Popularity> familyPopularities;

}
