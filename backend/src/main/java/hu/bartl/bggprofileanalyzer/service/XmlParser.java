package hu.bartl.bggprofileanalyzer.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.NamedEntity;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
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
                        .id(Integer.parseInt((String)bg.get("objectid")))
                        .name(parseName(bg.get("name")))
                        .yearpublished(Integer.parseInt((String) bg.get("yearpublished")))
                        .minplayers(Integer.parseInt((String) bg.get("minplayers")))
                        .maxplayers(Integer.parseInt((String) bg.get("maxplayers")))
                        .playingtime(Integer.parseInt((String) bg.get("playingtime")))
                        .minplaytime(Integer.parseInt((String) bg.get("minplaytime")))
                        .maxplaytime(Integer.parseInt((String) bg.get("maxplaytime")))
                        .age(Integer.parseInt((String) bg.get("age")))
                        .thumbnail((String) bg.get("thumbnail"))
                        .image((String) bg.get("image"))
                        .artists(parseNameEntities(bg.get("boardgameartist")))
                        .designers(parseNameEntities(bg.get("boardgamedesigner")))
                        .categories(parseNameEntities(bg.get("boardgamecategory")))
                        .families(parseNameEntities(bg.get("boardgamefamily")))
                        .mechanics(parseNameEntities(bg.get("boardgamemechanic")))
                        .subDomains(parseNameEntities(bg.get("boardgamesubdomain")))
                        .expansions(parseNameEntities(bg.get("boardgameexpansion")));

        Map<String, Object> ratings = (Map<String, Object>) ((Map<String, Object>) bg.get("statistics")).get("ratings");
        processRatings(gameBuilder, ratings);
        return gameBuilder.build();
    }

    private Set<NamedEntity> parseNameEntities(Object entyNode) {
        Set<NamedEntity> result = new HashSet<>();
        if (entyNode != null) {
            if (entyNode instanceof List) {
                ((List) entyNode)
                        .stream()
                        .map(this::parseNamedEntity)
                        .forEach(e -> result.add((NamedEntity) e));
            } else {
                result.add(parseNamedEntity(entyNode));
            }
        }
        return result;
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
        builder.userRating(Double.parseDouble((String) ratings.get("usersrated")))
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
