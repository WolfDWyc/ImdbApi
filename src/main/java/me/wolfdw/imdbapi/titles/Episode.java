package me.wolfdw.imdbapi.titles;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import me.wolfdw.imdbapi.IMDb;

import java.io.IOException;

public class Episode extends Title {

    @JsonSerialize(using=ParentSerializer.class, as=Title.class)
    private Title parent;
    private int seasonNumber;
    private int episodeNumber;

     public Episode(Title t) {
         super(t);
     }

     public void addInfo(Title parent, int seasonNumber, int episodeNumber) {
         this.parent = parent;
         this.seasonNumber = seasonNumber;
         this.episodeNumber = episodeNumber;
     }

    public Title getParent() {
        return parent;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }


    public void setEpisodeNumber(int episodeNumber) {
         this.episodeNumber = episodeNumber;
    }

    public static void buildEpisode(String[] episodeInfo, IMDb imdb) {
        Episode episode = (Episode) imdb.getTitle(episodeInfo[0]);
        Title parent = imdb.getTitle(episodeInfo[1]);
        Series parentSeries = null;
        if (parent != null) {
            if (!(parent instanceof Series)) {
                parentSeries = new Series(parent);
                imdb.addTitle(parentSeries, true);
            } else
                parentSeries = (Series) parent;
        }

        int seasonNumber = episodeInfo[2].equals("\\N") ? 1 : Integer.parseInt(episodeInfo[2]);
        int episodeNumber = episodeInfo[3].equals("\\N") ? -1 : Integer.parseInt(episodeInfo[3]);
        episode.addInfo(parentSeries, seasonNumber, episodeNumber);
        if (parentSeries != null)
            parentSeries.addEpisode(episode);

    }


    @Override
    public String toString() {
        return "Episode{" +
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
                ", parent=" + (parent != null ? parent.id : null) +
                ", seasonNumber=" + seasonNumber +
                ", episodeNumber=" + episodeNumber +
                '}';
    }

    public static class ParentSerializer extends JsonSerializer<Title> {

        @Override
        public void serialize(Title tempTitle,
                              JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider)
                throws IOException {
            jsonGenerator.writeObject(tempTitle.getId());
        }
    }
}
