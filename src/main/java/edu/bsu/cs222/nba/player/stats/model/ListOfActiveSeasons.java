package edu.bsu.cs222.nba.player.stats.model;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ListOfActiveSeasons {

    private ListOfActiveSeasons(){}

    public static ListOfActiveSeasons create(){
        return new ListOfActiveSeasons();
    }

    public JSONArray parseYears(InputStream stream){
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(stream, "UTF-8");
        return JsonPath.read(document, "$..season[?(@.seasonYear)].seasonYear");
    }

    public List<Integer> getYearsAsList(InputStream stream){
        JSONArray array = parseYears(stream);
        ArrayList<Integer> list = new ArrayList<>();
            for (Object element : array) {
                list.add(Integer.parseInt(element.toString()));
        }
        return list;
    }
    public int getNumberOfActiveSeasons(InputStream stream){
        return parseYears(stream).size();
    }
}
