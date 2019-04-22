package hu.bartl.bggprofileanalyzer.data;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder(toBuilder = true)
public class Popularity {

    private final Integer entityId;
    private final String entityName;
    private final Double gamesRatio;
    private final Set<NamedEntityWithPicture> gamesInGroup;

    public Integer getGamesCount() {
        return gamesInGroup.size();
    }
}
