package hu.bartl.bggprofileanalyzer.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class UserStats {
    
    private final String userId;
    
    private final List<Popularity> categoryPopularities;
    private final List<Popularity> mechanicPopularities;
    private final List<Popularity> designerPopularities;
    private final List<Popularity> subDomainPopularities;
    private final List<Popularity> artistPopularities;
    private final List<Popularity> familyPopularities;
    
    @Data
    @Builder
    public static class Popularity {
        
        private final Integer entityId;
        private final String entityName;
        private final Double gamesRatio;
        private final Set<NamedEntity> gamesInGroup;

        public Integer getGamesCount() {
            return gamesInGroup.size();
        }
    }
}
