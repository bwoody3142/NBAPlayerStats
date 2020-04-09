package edu.bsu.cs222.nba.player.stats.model;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ListOfTeams {

    private ListOfTeams(){}

    public static ListOfTeams getNewListOfTeams(){
        return new ListOfTeams();
    }

    private JSONArray getTeamListArray(Integer year) throws IOException {
        InputStream stream = URLCreator.createEmptyUrl().createTeamListStream(year);
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(stream, "UTF-8");
        return JsonPath.read(document, "$..standard[?(@.isNBAFranchise==true)].fullName");
    }

    public List<String> createFullListOfTeams(Integer year) throws IOException {
        ArrayList<String> teamList = new ArrayList<>();
        for (int i = 0; i < getTeamListArray(year).size(); i++) {
            teamList.add(getTeamListArray(year).get(i).toString());
        }
        return teamList;
    }
}
