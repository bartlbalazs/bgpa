package hu.bartl.bggprofileanalyzer.stats;

public enum Stats {

    ARTIST, CATEGORY, DESIGNER, FAMILY, MECHANISM, SUBDOMAIN,
    ARTIST_WITHOUT_EXPANSIONS, CATEGORY_WITHOUT_EXPANSIONS, DESIGNER_WITHOUT_EXPANSIONS, FAMILY_WITHOUT_EXPANSIONS,
    MECHANISM_WITHOUT_EXPANSIONS, SUBDOMAIN_WITHOUT_EXPANSIONS;

    private static final String WITHOUT_EXPANSIONS = "WITHOUT_EXPANSIONS";

    public static Stats withoutExpansions(Stats stats) {
        return Stats.valueOf(stats.name() + "_" + WITHOUT_EXPANSIONS);
    }
}
