package hu.bartl.bggprofileanalyzer;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.assertj.core.util.Sets;

import hu.bartl.bggprofileanalyzer.data.BoardGame;
import hu.bartl.bggprofileanalyzer.data.NamedEntity;

@UtilityClass
public class TestHelper {
    
    public static final String SAMPLE_USER_COLLECTION = "bgg_sample_user_collection.xml";
    public static final String SAMPLE_USER_GAMES_DETAILS = "bgg_sample_user_games_details.xml";
    
    @SneakyThrows
    static String getTestFileContent(String fileName) {
        Path path = Paths.get(TestHelper.class.getClassLoader().getResource(fileName).toURI());
        List<String> lines = Files.readAllLines(path);
        return lines.stream().collect(Collectors.joining());
    }
    
    static Set<BoardGame> getExpectedGames() {
        HashSet<BoardGame> boardGames = new HashSet<>();
        boardGames.add(BoardGame.builder()
                                .id(39351)
                                .name("Automobile")
                                .minplayers(3)
                                .maxplayers(5)
                                .minplaytime(120)
                                .maxplaytime(120)
                                .age(12)
                                .playingtime(120)
                                .yearpublished(2009)
                                .userRating(7.35373)
                                .weight(3.5255)
                                .rank(344)
                                .familyRank(213)
                                .owned(4905)
                                .thumbnail("https://cf.geekdo-images.com/thumb/img/5cz_OHBQraTUo259u9UqdnLlkRk=/fit-in/200x150/pic744514.jpg")
                                .image("https://cf.geekdo-images.com/original/img/RwQJMGiRADlWRiee_AiijICTDxg=/0x0/pic744514.jpg")
                                .artist(Sets.newLinkedHashSet(
                                        NamedEntity.builder().id(12516).name("Mike Atkinson").build(),
                                        NamedEntity.builder().id(3262).name("Czarnè").build(),
                                        NamedEntity.builder().id(11802).name("Peter Dennis").build(),
                                        NamedEntity.builder().id(11507).name("Klemens Franz").build()
                                ))
                                .designers(Sets.newLinkedHashSet(
                                        NamedEntity.builder().id(6).name("Martin Wallace").build()
                                ))
                                .expansions(Sets.newLinkedHashSet(
                                        NamedEntity.builder().id(171216).name("Automobile: Chicken – 2 Player Variant").build(),
                                        NamedEntity.builder().id(151122).name("Mayfair Games' Limited Edition Promo Expansion Set #6").build()
                                ))
                                .families(Sets.newLinkedHashSet(
                                        NamedEntity.builder().id(21991).name("Automotive").build(),
                                        NamedEntity.builder().id(99).name("Treefrog Line").build()
                                ))
                                .mechanics(Sets.newLinkedHashSet(
                                        NamedEntity.builder().id(2013).name("Commodity Speculation").build(),
                                        NamedEntity.builder().id(2015).name("Variable Player Powers").build()
                                ))
                                .categories(Sets.newLinkedHashSet(
                                        NamedEntity.builder().id(1021).name("Economic").build(),
                                        NamedEntity.builder().id(1021).name("Industry / Manufacturing").build()
                                ))
                                .subDomains(Sets.newLinkedHashSet(
                                        NamedEntity.builder().id(5497).name("Strategy Games").build()
                                ))
                                .build());
        
        boardGames.add(getSingleBoardGame());
    
        boardGames.add(BoardGame.builder()
                                .id(111799)
                                .name("Gran Hotel Austria<")
                                .minplayers(1)
                                .maxplayers(4)
                                .minplaytime(180)
                                .maxplaytime(180)
                                .age(0)
                                .playingtime(180)
                                .yearpublished(2013)
                                .userRating(7.76623)
                                .weight(3.5185)
                                .rank(481)
                                .familyRank(36)
                                .owned(3826)
                                .thumbnail("https://cf.geekdo-images.com/thumb/img/qA7oZwXEKpbhnqAYWjPvyvogreo=/fit-in/200x150/pic1723711.jpg")
                                .image("https://cf.geekdo-images.com/original/img/mJmwNE2qefn1yUfwfzVaIMA0qaU=/0x0/pic1723711.jpg")
                                .artist(Sets.newLinkedHashSet(
                                        NamedEntity.builder().id(49323).name("Xavier Carrascosa").build(),
                                        NamedEntity.builder().id(34490).name("Chechu Nieto").build(),
                                        NamedEntity.builder().id(71).name("Rodger B. MacGowan").build()
                                ))
                                .designers(Sets.newLinkedHashSet(
                                        NamedEntity.builder().id(5659).name("Jeff Grossman").build(),
                                        NamedEntity.builder().id(772).name("Volko Ruhnke").build()
                                ))
                                .expansions(Sets.newLinkedHashSet(
                                        NamedEntity.builder().id(180284).name("Invierno Cubano: Castro's Counterinsurgency, 1959-1965").build()
                                ))
                                .families(Sets.newLinkedHashSet(
                                        NamedEntity.builder().id(10648).name("Country: Cuba").build(),
                                        NamedEntity.builder().id(18749).name("GMT COIN Series").build(),
                                        NamedEntity.builder().id(5666).name("Solitaire Games").build(),
                                        NamedEntity.builder().id(8164).name("Solitaire Wargames").build(),
                                        NamedEntity.builder().id(46192).name("Historical Figures: Fidel Castro").build()
                                ))
                                .mechanics(Sets.newLinkedHashSet(
                                        NamedEntity.builder().id(2080).name("Area Control / Area Influence").build(),
                                        NamedEntity.builder().id(2046).name("Area Movement").build(),
                                        NamedEntity.builder().id(2018).name("Campaign / Battle Card Driven").build(),
                                        NamedEntity.builder().id(2072).name("Dice Rolling").build()
                                ))
                                .categories(Sets.newLinkedHashSet(
                                        NamedEntity.builder().id(1021).name("Economic").build(),
                                        NamedEntity.builder().id(1069).name("Modern Warfare").build(),
                                        NamedEntity.builder().id(1001).name("Political").build(),
                                        NamedEntity.builder().id(1019).name("Wargame").build()
                                ))
                                .subDomains(Sets.newLinkedHashSet(
                                        NamedEntity.builder().id(4664).name("Wargames").build()
                                ))
                                .build());
        
        return boardGames;
    }
    
    public static BoardGame getSingleBoardGame() {
        return BoardGame.builder()
                        .id(182874)
                        .name("Grand Austria Hotel")
                        .minplayers(2)
                        .maxplayers(4)
                        .minplaytime(60)
                        .maxplaytime(120)
                        .age(12)
                        .playingtime(120)
                        .yearpublished(2015)
                        .userRating(7.7337)
                        .weight(3.2251)
                        .rank(100)
                        .familyRank(66)
                        .owned(9198)
                        .thumbnail("https://cf.geekdo-images.com/thumb/img/NOZr-fKBWO7WqdrEIObnGu5aPIo=/fit-in/200x150/pic2728138.jpg")
                        .image("https://cf.geekdo-images.com/original/img/4d_XZzAStbyODa6p8xhLyOpIMh8=/0x0/pic2728138.jpg")
                        .artist(Sets.newLinkedHashSet(
                                NamedEntity.builder().id(11507).name("Klemens Franz").build()
                                ))
                        .designers(Sets.newLinkedHashSet(
                                        NamedEntity.builder().id(6817).name("Virginio Gigli").build(),
                                        NamedEntity.builder().id(35418).name("Simone Luciani").build()
                                ))
                        .expansions(Sets.newLinkedHashSet(
                                        NamedEntity.builder().id(238724).name("Grand Austria Hotel: Herr Moras").build(),
                                        NamedEntity.builder().id(238742).name("Grand Austria Hotel: Mag. Ferdinand").build()
                                ))
                        .families(Sets.newLinkedHashSet(
                                        NamedEntity.builder().id(18111).name("Cities: Vienna").build(),
                                        NamedEntity.builder().id(12981).name("Country: Austria").build()
                                ))
                        .mechanics(Sets.newLinkedHashSet(
                                        NamedEntity.builder().id(2072).name("Dice Rolling").build(),
                                        NamedEntity.builder().id(2040).name("Hand Management").build(),
                                        NamedEntity.builder().id(2004).name("Set Collection").build()
                                ))
                        .categories(Sets.newLinkedHashSet(
                                        NamedEntity.builder().id(1021).name("Economic").build()
                                ))
                        .subDomains(Sets.newLinkedHashSet(
                                        NamedEntity.builder().id(5497).name("Strategy Games").build()
                                ))
                        .build();
    }
}