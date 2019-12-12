package hu.bartl.bggprofileanalyzer.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.ChartData;
import hu.bartl.bggprofileanalyzer.data.NamedEntityWithCoordinates;
import hu.bartl.bggprofileanalyzer.data.StreamHelper;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GamesToChartDataConverter {

    private StreamHelper streamHelper;

    public List<ChartData> gamesToPlayercountComplexityChart(Collection<BoardGame> games) {
        Map<String, ChartData> subdomainMap = Maps.newHashMap();
        games.forEach(g -> {
            NamedEntityWithCoordinates proto = NamedEntityWithCoordinates.withCoordinates()
                .id(g.getId())
                .name(g.getName())
                .thumbnail(g.getThumbnail())
                .x(g.getWeight())
                .r(g.getUserRating()).build();
            g.getPlayerCountRecommendations().forEach(r -> {
                if (r.isBest()) {
                    g.getSubDomains().forEach(s -> {
                        ChartData subdomainGroup =
                            subdomainMap.getOrDefault(s.getName(), ChartData.of(s.getName(), Lists.newArrayList()));
                        subdomainGroup.getSeries().add(proto.toBuilder().y(r.getNumplayers()).build());
                        subdomainMap.put(s.getName(), subdomainGroup);
                    });
                }
            });
        });
        List<ChartData> result = Lists.newArrayList(subdomainMap.values());
        result.sort(Comparator.comparing((ChartData c) -> c.getSeries().size()).reversed());
        return result;
    }
}
