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

import static edu.bsu.cs222.nba.player.stats.model.PlayerMap.createEmptyPlayerMap;

public class TeamRoster {


    private final Map<String, Integer> map = createEmptyPlayerMap().createMapOfPlayersWithID();
    private final BiMap<Integer, String> invertedMap = HashBiMap.create(map).inverse();
    private final Map<Integer, String> roster = new HashMap<>();
    private final String team;

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

    public Map<Integer, String> createRoster() throws IOException {
        for(int i = 0; i < getPersonIdArray().size(); i++){
            String personIdAsString = getPersonIdArray().get(i).toString();
            int personID = Integer.parseInt(personIdAsString);
            String fullName = invertedMap.get(personID);
            if (invertedMap.containsKey(personID)) {
                roster.put(personID, fullName);
            }
        }
        return roster;
    }
}
