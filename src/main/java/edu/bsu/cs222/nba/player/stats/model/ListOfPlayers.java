package edu.bsu.cs222.nba.player.stats.model;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ListOfPlayers {

    private Map<String, String> playerMap = new HashMap<>();

    private ListOfPlayers() {}

    public static ListOfPlayers createEmptyListOfPlayers(){
        return new ListOfPlayers();
    }

    private JSONArray getPlayerListArray() throws Exception {
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

    public Map<String, String> createFullListOfPlayers() throws Exception {
        for (Object unCastedMap : getPlayerListArray()) {
            //Had to suppress due to the api
            @SuppressWarnings("unchecked") Map<String, JSONObject> map = (Map<String, JSONObject>) unCastedMap;
            playerMap.put(getFullName(map), getPersonID(map));
        }
        return playerMap;
    }
}



