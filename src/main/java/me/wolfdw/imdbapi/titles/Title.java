package me.wolfdw.imdbapi.titles;

import java.util.Arrays;
import java.util.List;

public class Title {

    final String id;
    final TitleType type;
    final String name;
    final String originalName;
    final boolean adult;
    final int startYear;
    final int endYear;
    final int episodeLength;
    final List<String> genres;
    float averageRating;
    int numVotes;

    private Title(String id, TitleType type, String name, String originalName, boolean adult, int startYear, int endYear, int episodeLength, List<String> genres, float averageRating, int numVotes) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.originalName = originalName;
        this.adult = adult;
        this.startYear = startYear;
        this.endYear = endYear;
        this.episodeLength = episodeLength;
        this.genres = genres;
        this.averageRating = averageRating;
        this.numVotes = numVotes;
    }

    public Title(Title t) {
        this(t.id, t.type, t.name, t.originalName, t.adult, t.startYear, t.endYear, t.episodeLength, t.genres, t.averageRating, t.numVotes);

    }

    public void addRating(float averageRating, int numVotes) {
        this.averageRating = averageRating;
        this.numVotes = numVotes;
    }

    public String getId() {
        return id;
    }

    public TitleType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public boolean isAdult() {
        return adult;
    }

    public int getStartYear() {
        return startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public int getEpisodeLength() {
        return episodeLength;
    }

    public List<String> getGenres() {
        return genres;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public int getNumVotes() {
        return numVotes;
    }

    @Override
    public String toString() {
        return "Title{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", originalName='" + originalName + '\'' +
                ", adult=" + adult +
                ", startYear=" + startYear +
                ", endYear=" + endYear +
                ", episodeLength=" + episodeLength +
                ", genres=" + genres +
                ", averageRating=" + averageRating +
                ", numVotes=" + numVotes +
                '}';
    }


    public static Title build(String[] titleInfo) {
        TitleType type = TitleType.titleTypeByName(titleInfo[1]);
        boolean adult = titleInfo[4].equals("1");
        int startYear = titleInfo[5].equals("\\N") ? 0 : Integer.parseInt(titleInfo[5]);
        int endYear = titleInfo[6].equals("\\N") ? 0 : Integer.parseInt(titleInfo[6]);
        int episodeLength = titleInfo[7].equals("\\N") ? 0 : Integer.parseInt(titleInfo[7]);
        List<String> genres = titleInfo[8].equals("\\N") ? null : Arrays.asList(titleInfo[8].split(","));
        Title t = new Title(titleInfo[0],type, titleInfo[2], titleInfo[3], adult, startYear, endYear, episodeLength, genres, 0, 0);
        if (t.getType() == TitleType.TV_SERIES || t.getType() == TitleType.TV_MINI_SERIES)
            t = new Series(t);

        if (t.getType() == TitleType.TV_EPISODE)
            t = new Episode(t);

        return t;
    }


}
