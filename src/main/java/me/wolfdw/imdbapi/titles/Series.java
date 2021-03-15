package me.wolfdw.imdbapi.titles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Series extends Title {


    private final List<List<Episode>> seasons;

    public Series(Title t) {
        super(t);
        seasons = new ArrayList<>();
    }

    public Episode getEpisode(int seasonNumber, int episodeNumber) {
        Episode e = seasons.get(seasonNumber).get(episodeNumber);
        if (e == null)
            return null;
        if (e.getSeasonNumber() != seasonNumber || e.getEpisodeNumber() != episodeNumber)
            System.out.println("ALERT1");
        return e;
    }

    public List<Episode> getSeason(int seasonNumber) {
        List<Episode> season = seasons.get(seasonNumber);
        if (season.size() == 0)
            return season;
        if (season.get(1).getSeasonNumber() != seasonNumber)
            System.out.println("ALERT2");
        return season.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    @JsonProperty("seasonCount")
    public int getNumberOfSeasons() {
        int num = 0;
        for (List<Episode> season : seasons)
            if (season.size() > 0)
                num++;

        return num;
    }

    @JsonProperty("episodeCount")
    public int getNumberOfEpisodes() {
        int num = 0;
        for (List<Episode> season : seasons) {
            num += season.stream().filter(Objects::nonNull).count();
        }

        return num;
    }

    @JsonProperty("episodes")
    public List<Episode> getAllEpisodes() {
        List<Episode> allEpisodes = new ArrayList<>();
        for (List<Episode> season : seasons) {
            season.forEach(e -> {
                if (e != null)
                    allEpisodes.add(e);
            });
        }
        return allEpisodes;
    }


    @JsonIgnore
    public List<List<Episode>> getAllSeasons() {
        List<List<Episode>> allSeasons = new ArrayList<>();
        for (List<Episode> season : seasons) {
            if (season.size() > 0)
                allSeasons.add(season);
        }
        return allSeasons;
    }


    public void addEpisode(Episode e) {
        while (seasons.size() <= e.getSeasonNumber())
            seasons.add(new ArrayList<>());

        List<Episode> season = seasons.get(e.getSeasonNumber());

        if (e.getEpisodeNumber() == -1) {
            if (season.size() == 0)
                season.add(null);
            season.add(e);
            e.setEpisodeNumber(season.size());
        }
        else {
            while (season.size() <= e.getEpisodeNumber())
                season.add(null);

            season.set(e.getEpisodeNumber(), e);
        }
    }

    @Override
    public String toString() {
        return "Series{" +
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
                ", seasonNumber=" + getNumberOfSeasons() +
                ", episodeNumber=" + getNumberOfEpisodes() +
                '}';
    }
}
