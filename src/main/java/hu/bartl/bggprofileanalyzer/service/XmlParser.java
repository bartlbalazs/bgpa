package hu.bartl.bggprofileanalyzer.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import org.springframework.stereotype.Service;

import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.NamedEntity;

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
        BoardGame.BoardGameBuilder gameBuilder = BoardGame.builder()
                                                          .id(Integer.valueOf((String) bg.get("objectid")))
                                                          .name(parseName(bg.get("name")))
                                                          .yearpublished(Integer.valueOf((String) bg.get("yearpublished")))
                                                          .minplayers(Integer.valueOf((String) bg.get("minplayers")))
                                                          .maxplayers(Integer.valueOf((String) bg.get("maxplayers")))
                                                          .playingtime(Integer.valueOf((String) bg.get("playingtime")))
                                                          .minplaytime(Integer.valueOf((String) bg.get("minplaytime")))
                                                          .maxplaytime(Integer.valueOf((String) bg.get("maxplaytime")))
                                                          .age(Integer.valueOf((String) bg.get("age")))
                                                          .thumbnail((String) bg.get("thumbnail"))
                                                          .image((String) bg.get("image"))
                                                          .artist(parseNameEntities(bg.get("boardgameartist")))
                                                          .designers(parseNameEntities(bg.get("boardgamedesigner")))
                                                          .categories(parseNameEntities(bg.get("boardgamecategory")))
                                                          .families(parseNameEntities(bg.get("boardgamefamily")))
                                                          .mechanics(parseNameEntities(bg.get("boardgamemechanic")))
                                                          .subDomains(parseNameEntities(bg.get("boardgamesubdomain")))
                                                          .expansions(parseNameEntities(bg.get("boardgameexpansion")));
        
        Map<String, Object> ratings = (Map<String, Object>) ((Map<String, Object>) bg.get("statistics")).get("ratings");
        processRatings(gameBuilder, ratings);
        BoardGame boardGame = gameBuilder.build();
        return boardGame;
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
        return NamedEntity.builder().id(Integer.valueOf(entityMap.get("objectid"))).name(entityMap.get("")).build();
    }
    
    private String parseName(Object names) {
        if (names instanceof List) {
            return ((List<Map<String, String>>) names).stream()
                                                      .filter(n -> "true".equals(n.get("primary")))
                                                      .findFirst()
                                                      .map(n -> String.valueOf(n.get("")))
                                                      .get();
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
        builder.userRating(Double.valueOf((String) ratings.get("usersrated")))
               .weight(Double.valueOf((String) ratings.get("averageweight")))
               .owned(Integer.valueOf((String) ratings.get("owned")));
    }
    
    private Optional<Map<?, ?>> filterForType(List ranks, String typeName) {
        return (Optional<Map<?, ?>>) ((List) ranks).stream().filter(r -> {
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
