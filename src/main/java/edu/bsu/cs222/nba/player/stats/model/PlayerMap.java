package edu.bsu.cs222.nba.player.stats.model;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class PlayerMap {

    private final Map<String, Integer> playerMap = new HashMap<>();

    private PlayerMap() {}

    public static PlayerMap createEmptyPlayerMap(){
        return new PlayerMap();
    }

    private JSONArray getPlayerListArray() throws IOException {
        InputStream stream = URLCreator.createEmptyUrl().createPlayerListStream(2019);
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(stream, "UTF-8");
        return JsonPath.read(document, "$..standard.*");
    }

    private String getFullName(Map<String, JSONObject> map) {
        Object firstName = map.get("firstName");
        Object lastName = map.get("lastName");
        return firstName.toString() + " " + lastName.toString();
    }

    private String getPersonID(Map<String, JSONObject> map) {
        Object object = map.get("personId");
        return object.toString();
    }

    public Map<String, Integer> createMapOfPlayersWithID() throws IOException {
        for (Object unCastedMap : getPlayerListArray()) {
            //Had to suppress because the API returns a raw object, instead we want a Map<String, JSONObject>.
            @SuppressWarnings("unchecked") Map<String, JSONObject> map = (Map<String, JSONObject>) unCastedMap;
            String fullName = getFullName(map);
            int personID = Integer.parseInt(getPersonID(map));
            playerMap.put(fullName, personID);
        }
        return playerMap;
    }
}



