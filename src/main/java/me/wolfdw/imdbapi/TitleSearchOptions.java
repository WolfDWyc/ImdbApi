package me.wolfdw.imdbapi;

import me.wolfdw.imdbapi.titles.TitleType;

import java.util.List;

public class TitleSearchOptions {
    private final String name;
    private final TitleType titleType;
    private final List<String> genres;
    private final int amount;
    private final int minVotes;
    private final float minRating;
    private final int order;


    public TitleSearchOptions(String name, TitleType titleType, List<String> genres, int amount, int minVotes, float minRating, int order) {
        this.name = name;
        this.titleType = titleType;
        this.genres = genres;
        this.amount = amount;
        this.minVotes = minVotes;
        this.minRating = minRating;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public TitleType getTitleType() {
        return titleType;
    }

    public List<String> getGenres() {
        return genres;
    }

    public int getAmount() {
        return amount;
    }

    public int getMinVotes() {
        return minVotes;
    }

    public float getMinRating() {
        return minRating;
    }

    public int getOrder() {
        return order;
    }
}
