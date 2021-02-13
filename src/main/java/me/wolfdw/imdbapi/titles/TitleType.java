package me.wolfdw.imdbapi.titles;

import java.util.Arrays;
import java.util.List;

public enum TitleType {

    MOVIE,SHORT,VIDEO,TV_SERIES,TV_MINI_SERIES,TV_SHORT,TV_MOVIE,VIDEO_GAME, TV_SPECIAL, TV_EPISODE ,UNKNOWN;

    private static final List<TitleType> enumTitleTypes = Arrays.asList(MOVIE, SHORT, VIDEO, TV_SERIES, TV_MINI_SERIES, TV_SHORT, TV_MOVIE, VIDEO_GAME, TV_SPECIAL, TV_EPISODE, UNKNOWN);
    private static final List<String> stringTitleTypes = Arrays.asList("movie", "short", "video", "tvSeries", "tvMiniSeries", "tvShort", "tvMovie", "videoGame", "tvSpecial", "tvEpisode", "unknown");
    private static final List<String> stringTitleTypeIds = Arrays.asList("MOVIE","SHORT","VIDEO","TV_SERIES","TV_MINI_SERIES","TV_SHORT","TV_MOVIE","VIDEO_GAME","TV_SPECIAL","TV_EPISODE","UNKNOWN");

    
    public static TitleType titleTypeByName(String name) {
        int index = stringTitleTypes.indexOf(name);
        if (index == -1)
            return TitleType.UNKNOWN;
        return enumTitleTypes.get(index);
    }

    public static TitleType titleTypeById(String id) {
        int index = stringTitleTypeIds.indexOf(id);
        if (index == -1)
            return TitleType.UNKNOWN;
        return enumTitleTypes.get(index);
    }
}
