package me.wolfdw.imdbapi;

import me.wolfdw.imdbapi.titles.Title;
import me.wolfdw.imdbapi.titles.TitleType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Title> topEntries(TitleSearchOptions searchOptions) {

        List<Title> resultList = titleList.stream().filter(e -> filterTitle(e, searchOptions.getName(),
                searchOptions.getTitleType(), searchOptions.getGenres(), searchOptions.getMinVotes(),
                searchOptions.getMinRating())).limit(searchOptions.getAmount()).collect(Collectors.toList());


        if (searchOptions.getOrder() != 0) {
            int reverse = searchOptions.getOrder() == 2 ? -1 : 1;
            resultList.sort((x,y) -> titleComparator(x, y, reverse));
        }
        return resultList;
    }

    private boolean filterTitle(Title e, String name, TitleType titleType, List<String> genres, int voteNum, float rating) {
        if (name != null && !e.getName().equals(name))
            return false;
        if (titleType != null && e.getType() != titleType)
            return false;
        if (genres != null && (e.getGenres() == null ||
                !e.getGenres().containsAll(genres)))
            return false;
        if (e.getNumVotes() < voteNum)
            return false;
        return !(e.getAverageRating() < rating);
    }


    private int titleComparator(Title t1, Title t2, int reverse) {
        if (t2.getAverageRating() == t1.getAverageRating())
            return 0;
        if (t2.getAverageRating() > t1.getAverageRating())
            return -1 * reverse;
        return reverse;
    }

}
