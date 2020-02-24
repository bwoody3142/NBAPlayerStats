package edu.bsu.cs222.NBAPlayerStats;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TestHelloWorld {

    private Object document;

    @BeforeEach
    public void setup() {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("PlayerList2018.json");
        document = Configuration.defaultConfiguration().jsonProvider().parse(in, "UTF-8");
    }

    @Test
    public void testGetFirstPlayerCode(){
        JSONArray array = JsonPath.read(document, "$..standard.*");
        @SuppressWarnings("unchecked")
        Map<String,JSONObject> object = (Map<String,JSONObject>)array.get(0);
        Object object2 = object.get("teamSitesOnly");
        Map<String, String> map2 = (Map<String, String>)object2;
        String name = map2.get("playerCode");
        Assertions.assertEquals("jaylen_adams", name);
    }

    @Test
    public void testGetFirstPlayerID(){
        JSONArray array = JsonPath.read(document, "$..standard.*");
        @SuppressWarnings("unchecked")
        Map<String,JSONObject> object = (Map<String,JSONObject>)array.get(0);
        Object object2 = object.get("personId");
        String playerID = object2.toString();
        Assertions.assertEquals("1629121", playerID);
    }

    @Test
    public void testGetFirstPlayer(){
        JSONArray array = JsonPath.read(document, "$..standard.*");
        @SuppressWarnings("unchecked")
        Map<String,JSONObject> object = (Map<String,JSONObject>)array.get(0);
        Object object2 = object.get("teamSitesOnly");
        Map<String, String> map2 = (Map<String, String>)object2;
        String playerCode = map2.get("playerCode");
        Object object3 = object.get("personId");
        String playerID = object3.toString();
        Map<String, String> player = new HashMap<String, String>();
        player.put(playerCode, playerID);
        Assertions.assertNotNull(player);
        Assertions.assertEquals("jaylen_adams",player.entrySet().iterator().next().getKey());
        Assertions.assertEquals("1629121",player.entrySet().iterator().next().getValue());
    }

}
