package hu.bartl.bggprofileanalyzer.service;

import com.google.common.collect.Lists;
import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.NamedEntityWithCoordinates;
import hu.bartl.bggprofileanalyzer.data.StreamHelper;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GamesToChartDataConverter {

    private StreamHelper streamHelper;

    public List<NamedEntityWithCoordinates> gamesToPlayercountComplexityChart(Collection<BoardGame> games) {
        List<NamedEntityWithCoordinates> result = Lists.newArrayList();
        games.forEach(g -> {
            NamedEntityWithCoordinates proto = NamedEntityWithCoordinates.withCoordinates()
                .id(g.getId())
                .name(g.getName())
                .thumbnail(g.getThumbnail())
                .x(g.getWeight())
                .r(g.getUserRating() * 5).build();
            g.getPlayerCountRecommendations().forEach(r -> {
                if (r.isBest()) {
                    result.add(proto.toBuilder().y(r.getNumplayers()).build());
                }
            });
        });
        result.sort(Comparator.comparingDouble(NamedEntityWithCoordinates::getX));
        return result;
    }
}
