package hu.bartl.bggprofileanalyzer.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.NamedEntity;
import hu.bartl.bggprofileanalyzer.data.PlayerCountRecommendation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class XmlParser {

    private final XmlMapper xmlMapper;

    @SneakyThrows
    public Set<BoardGame> parseBoardGames(String boardgamesXml) {
        List<Map<?, ?>> xmlEntries = xmlMapper.readValue(boardgamesXml, List.class);
        return xmlEntries.stream().skip(1).map(this::parseBoardGame).collect(Collectors.toSet());
    }

    private BoardGame parseBoardGame(Map<?, ?> bg) {
        BoardGame.BoardGameBuilder gameBuilder =
                BoardGame.builder()
                        .id(Integer.parseInt(String.valueOf(bg.get("objectid"))))
                        .name(parseName(bg.get("name")))
                        .yearpublished(Integer.parseInt((String) bg.get("yearpublished")))
                        .minplayers(Integer.parseInt((String) bg.get("minplayers")))
                        .maxplayers(Integer.parseInt((String) bg.get("maxplayers")))
                        .playingtime(Integer.parseInt((String) bg.get("playingtime")))
                        .minplaytime(Integer.parseInt((String) bg.get("minplaytime")))
                        .maxplaytime(Integer.parseInt((String) bg.get("maxplaytime")))
                        .age(Integer.parseInt((String) bg.get("age")))
                        .thumbnail((String.valueOf(bg.get("thumbnail")).trim()))
                        .image(String.valueOf(bg.get("image")))
                        .artists(parseNamedEntities(bg.get("boardgameartist")))
                        .designers(parseNamedEntities(bg.get("boardgamedesigner")))
                        .categories(parseNamedEntities(bg.get("boardgamecategory")))
                        .families(parseNamedEntities(bg.get("boardgamefamily")))
                        .mechanics(parseNamedEntities(bg.get("boardgamemechanic")))
                        .subDomains(parseNamedEntities(bg.get("boardgamesubdomain")))
                        .expansions(parseNamedEntities(bg.get("boardgameexpansion")))
                        .playerCountRecommendations(parsePlayerCountRecommendations(bg.get("poll")));

        Map<String, Object> ratings = (Map<String, Object>) ((Map<String, Object>) bg.get("statistics")).get("ratings");
        processRatings(gameBuilder, ratings);
        return gameBuilder.build();
    }

    private Set<NamedEntity> parseNamedEntities(Object entityNode) {
        Set<NamedEntity> result = new HashSet<>();
        if (entityNode != null) {
            if (entityNode instanceof List) {
                ((List) entityNode)
                        .stream()
                        .map(this::parseNamedEntity)
                        .forEach(e -> result.add((NamedEntity) e));
            } else {
                result.add(parseNamedEntity(entityNode));
            }
        }
        return result;
    }

    private Set<PlayerCountRecommendation> parsePlayerCountRecommendations(Object poll) {
        if (!(poll instanceof List)) {
            return ImmutableSet.of();
        }

        return (Set<PlayerCountRecommendation>)
                ((List) poll)
                        .stream()
                        .filter(ps -> "suggested_numplayers".equals(((Map<String, Object>) ps).get("name")))
                        .map(p -> ((Map) p).get("results"))
                        .filter(rs -> rs instanceof List)
                        .flatMap(rs -> ((List) rs).stream())
                        .filter(r -> Pattern.matches("\\d+", (String) ((Map) r).get("numplayers")))
                        .map(r -> {
                            Map rMap = (Map) r;
                            return Pair.of(Integer.parseInt(String.valueOf(rMap.get("numplayers"))), rMap.get("result"));
                        })
                        .map(np -> {
                            Integer numberOfPlayers = (Integer) ((Pair) np).getFirst();
                            Map<String, Integer> pollResults = Maps.newHashMap();
                            List<Map<String, String>> rawResults = (List) ((Pair) np).getSecond();
                            for (Map<String, String> pollResultForPlayerCount : rawResults) {
                                pollResults.put(pollResultForPlayerCount.get("value"), Integer.parseInt(pollResultForPlayerCount.get("numvotes")));
                            }

                            return PlayerCountRecommendation.builder()
                                    .numplayers(numberOfPlayers)
                                    .best(pollResults.get("Best"))
                                    .recommended(pollResults.get("Recommended"))
                                    .notRecommended(pollResults.get("Not Recommended"))
                                    .build();
                        })
                        .collect(Collectors.toSet());
    }

    private NamedEntity parseNamedEntity(Object entityProperty) {
        Map<String, String> entityMap = (Map) entityProperty;
        return NamedEntity.builder().id(Integer.parseInt(entityMap.get("objectid"))).name(entityMap.get("")).build();
    }

    private String parseName(Object names) {
        if (names instanceof List) {
            return ((List<Map<String, String>>) names).stream()
                    .filter(n -> "true".equals(n.get("primary")))
                    .findFirst()
                    .map(n -> String.valueOf(n.get("")))
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Cannot choose primary name from names: %s", names)));
        }
        return String.valueOf(((Map) names).get(""));
    }

    @SneakyThrows
    private void processRatings(BoardGame.BoardGameBuilder builder, Map<String, Object> ratings) {
        Object ranks = ratings.get("ranks");
        if (ranks instanceof Map) {
            Object rank = ((Map) ranks).get("rank");
            if (rank instanceof List) {
                filterForType((List) rank, "subtype").ifPresent(r -> builder.rank(Integer.valueOf((String) r.get("value"))));
                filterForType((List) rank, "family").ifPresent(r -> builder.familyRank(Integer.valueOf((String) r.get("value"))));
            }

        }
        builder.userRating(Double.parseDouble((String) ratings.get("bayesaverage")))
                .weight(Double.parseDouble((String) ratings.get("averageweight")))
                .owned(Integer.parseInt((String) ratings.get("owned")));
    }

    private Optional<Map<?, ?>> filterForType(List ranks, String typeName) {
        return (Optional<Map<?, ?>>) (ranks).stream().filter(r -> {
            if (r instanceof Map) {
                Map rankMap = (Map) r;
                return typeName.equals(rankMap.get("type")) && !"Not Ranked".equals(rankMap.get("value"));
            } else {
                return false;
            }
        }).findFirst();
    }

    @SneakyThrows
    public Set<Integer> extractGameIds(String profileXml) {
        List profileMap = xmlMapper.readValue(profileXml, List.class);
        return (Set<Integer>) profileMap.stream()
                .skip(1)
                .map(bg -> Integer.valueOf((String) ((Map) bg).get("objectid")))
                .collect(Collectors.toSet());
    }
}
