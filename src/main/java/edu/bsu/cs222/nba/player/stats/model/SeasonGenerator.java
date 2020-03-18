package edu.bsu.cs222.nba.player.stats.model;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SeasonGenerator {

    private SeasonGenerator(){}

    public static SeasonGenerator create(){
        return new SeasonGenerator();
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
}
