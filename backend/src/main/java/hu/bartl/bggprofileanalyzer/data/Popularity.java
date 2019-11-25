package hu.bartl.bggprofileanalyzer.data;

import com.google.common.collect.Sets;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;
import java.util.Set;

@Data
@Builder(toBuilder = true)
public class Popularity {

    private final Optional<Integer> entityId;
    private final String entityName;
    private final Double gamesRatio;
    @Builder.Default
    private final Set<NamedEntityWithPicture> gamesInGroup = Sets.newHashSet();

    public Integer getGamesCount() {
        return gamesInGroup.size();
    }
}
