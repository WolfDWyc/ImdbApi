package me.wolfdw.imdbapi;

import me.wolfdw.imdbapi.titles.Title;
import me.wolfdw.imdbapi.titles.TitleType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IMDb {

    private final HashMap<String, Title> titleMap;
    private final List<Title> titleList;


    public IMDb() {
        titleMap = new HashMap<>();
        titleList = new ArrayList<>();
    }

    public boolean addTitle(Title title, boolean override) {
        if (!override && titleMap.containsKey(title.getId()))
            return false;
         titleMap.put(title.getId(), title);
         titleList.add(title);

        return true;
    }


    public Title getTitle(String id) {
        return titleMap.get(id);
    }

    public List<Title> searchTitle(String name) {
        return titleList.stream().filter(e -> e.getName().equals(name)).collect(Collectors.toList());
    }

    public List<Title> topEntries(String name, TitleType titleType, List<String> genres, int amount, int minVotes, float minRating, final int order) {
        int reverse = order == 2 ? -1 : 1;
        List<Title> resultList = null;
        Stream<Title> stream = titleList.stream().filter(e -> {
            if (name != null && !e.getName().equals(name))
                return false;
            if (titleType != null && e.getType() != titleType)
                return false;
            if (genres != null && (e.getGenres() == null || !e.getGenres().containsAll(genres)))
                return false;
            if (e.getNumVotes() < minVotes)
                return false;
            return !(e.getAverageRating() < minRating);
         });
        if (order == 0) {
            resultList = stream.collect(Collectors.toList());
        } else {
            resultList = stream.sorted((x, y) -> {
                        if (y.getAverageRating() == x.getAverageRating())
                            return 0;
                        if (y.getAverageRating() > x.getAverageRating())
                            return -1 * reverse;
                        return reverse;
                    }
            ).collect(Collectors.toList());
        }
        return resultList.subList(0, Math.min(amount,resultList.size()));
    }




    }
