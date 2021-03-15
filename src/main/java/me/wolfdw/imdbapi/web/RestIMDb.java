package me.wolfdw.imdbapi.web;

import me.wolfdw.imdbapi.IMDb;
import me.wolfdw.imdbapi.titles.Series;
import me.wolfdw.imdbapi.titles.Title;
import me.wolfdw.imdbapi.TitleSearchOptions;
import me.wolfdw.imdbapi.titles.TitleType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RestIMDb {
    public static IMDb imdb;

    @GetMapping(value = "/get", params = {"id"}) public Title getTitleById(@RequestParam(value = "id") String id,
                              @RequestParam(value="includeEpisodes", required = false, defaultValue = "true") boolean includeEpisodes) {
        return returnRequiredTitle(imdb.getTitle(id),includeEpisodes);
    }

    @GetMapping(value = "/get", params = {"ids"})
    public List<Title> getTitlesById(@RequestParam(value = "ids") List<String> ids,
                                     @RequestParam(value="includeEpisodes", required = false, defaultValue = "true") boolean includeEpisodes) {
        List<Title> titleList = new ArrayList<>();
        for(String id: ids)
            titleList.add(returnRequiredTitle(imdb.getTitle(id),includeEpisodes));
        return titleList;
    }


    @GetMapping(value = "/search")
    public List<Title> searchEntries(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                     @RequestParam(value = "type", required = false, defaultValue = "") String type,
                                     @RequestParam(value = "genres", required = false, defaultValue = "") List<String> genres,
                                     @RequestParam(value = "amount", required = false, defaultValue = "100") int amount,
                                     @RequestParam(value = "minVotes", required = false, defaultValue = "0") int minVotes,
                                     @RequestParam(value = "minRating", required = false, defaultValue = "0") float minRating,
                                     @RequestParam(value = "order", required = false, defaultValue = "none") String order,
                                     @RequestParam(value = "includeEpisodes", required = false, defaultValue = "false") boolean includeEpisodes) {
        long time = System.nanoTime();
        int orderParam = 0;
        if (order.equals("asc"))
            orderParam = 1;
        else if (order.equals("desc"))
            orderParam = 2;
        List<Title> titleList = new ArrayList<>();
        TitleSearchOptions searchOptions = new TitleSearchOptions( name.equals("") ? null : name,
                type.equals("") ? null : TitleType.titleTypeById(type), genres, amount,minVotes ,minRating, orderParam);
        for (Title t: imdb.topEntries(searchOptions)) {
            titleList.add(returnRequiredTitle(t,includeEpisodes));
        }
        System.out.println((System.nanoTime() - time));
        return titleList;
    }


    public Title returnRequiredTitle(Title t, boolean includeEpisodes) {
        if (!includeEpisodes && t instanceof Series)
            return new Title(t);
        return t;
    }

}
