package edu.bsu.cs222.nba.player.stats.model;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListOfActiveSeasons {

    private ListOfActiveSeasons(){}

    public static ListOfActiveSeasons create(){
        return new ListOfActiveSeasons();
    }

    public JSONArray parseYears(InputStream stream){
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(stream, "UTF-8");
        return JsonPath.read(document, "$..season[?(@.seasonYear)].seasonYear");
    }

    public List<Integer> getYearsAsListForUrl(InputStream stream){
        JSONArray array = parseYears(stream);
        ArrayList<Integer> list = new ArrayList<>();
            for (Object element : array) {
                list.add(Integer.parseInt(element.toString()));
        }
        return list;
    }

    public List<String> getSeasonsAsList(InputStream stream){
        JSONArray array = parseYears(stream);
        ArrayList<String> list = new ArrayList<>();
        for (Object element : array) {
            list.add(element.toString() + " - " + (Integer.parseInt(element.toString()) + 1));
        }
        return list;
    }

    public int getNumberOfActiveSeasons(InputStream stream){
        return parseYears(stream).size();
    }

    public Map<String, Integer> createListOfActiveSeasons(InputStream stream){
        Map<String, Integer> map = new HashMap<>();
        for(Object element : getYearsAsListForUrl(stream)){
            map.put(element.toString() + " - " + (Integer.parseInt(element.toString()) + 1), Integer.parseInt(element.toString()));
        }
        return map;
    }
}
