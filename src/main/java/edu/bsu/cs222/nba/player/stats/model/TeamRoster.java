package edu.bsu.cs222.nba.player.stats.model;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static edu.bsu.cs222.nba.player.stats.model.ListOfPlayers.createEmptyListOfPlayers;

public class TeamRoster {


    private Map<String, String> map = createEmptyListOfPlayers().createFullListOfPlayers();
    private BiMap<String, String> invertedMap = HashBiMap.create(map).inverse();
    private Map<String, String> roster = new HashMap<>();
    private String team;

    private TeamRoster(String team) throws IOException {
        this.team = team;
    }

    public static TeamRoster createTeamRoster(String team) throws IOException {
        return new TeamRoster(team);
    }

    public JSONArray getPersonIdArray() throws IOException {
        InputStream stream = URLCreator.createEmptyUrl().createTeamRosterStream(team);
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(stream, "UTF-8");
        return JsonPath.read(document, "$..standard.players.*.personId");
    }

    public Map<String, String> createRoster() throws IOException {
        for(int i = 0; i < getPersonIdArray().size(); i++){
            if (invertedMap.containsKey(String.valueOf(getPersonIdArray().get(i)))) {
                roster.put(getPersonIdArray().get(i).toString(), invertedMap.get(getPersonIdArray().get(i).toString()));
            }
        }
        return roster;
    }
}
