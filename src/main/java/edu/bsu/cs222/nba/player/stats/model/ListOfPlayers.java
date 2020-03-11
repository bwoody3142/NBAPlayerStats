package edu.bsu.cs222.nba.player.stats.model;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ListOfPlayers {
    private ListOfPlayers() {}

    public static ListOfPlayers createEmptyListOfPlayers(){
        return new ListOfPlayers();
    }

    private JSONArray getPlayerListArray(Integer year) throws Exception {
        URLCreator urlCreator = URLCreator.createEmptyUrl();
        InputStream stream = urlCreator.createPlayerListStream(year);
        Object playerListDoc = Configuration.defaultConfiguration().jsonProvider().parse(stream, "UTF-8");
        return JsonPath.read(playerListDoc, "$..standard.*");
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

    public Map<String, String> createFullListOfPlayers(Integer year) throws Exception {
        Map<String, String> playerMap = new HashMap<>();
        for (Object unCastedMap : getPlayerListArray(year)) {
            //Had to suppress due to the api
            @SuppressWarnings("unchecked") Map<String, JSONObject> map = (Map<String, JSONObject>) unCastedMap;
            playerMap.put(getFullName(map), getPersonID(map));
        }
        return playerMap;
    }
}



