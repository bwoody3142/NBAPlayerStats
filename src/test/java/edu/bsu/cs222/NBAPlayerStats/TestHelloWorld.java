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
        Map<String,JSONObject> map = (Map<String,JSONObject>)array.get(0);
        Object firstName = map.get("firstName");
        Object lastName = map.get("lastName");
        String fullName = firstName.toString() + lastName.toString();
        Assertions.assertEquals("JaylenAdams", fullName);
    }

    @Test
    public void testGetFirstPlayerID(){
        JSONArray array = JsonPath.read(document, "$..standard.*");
        @SuppressWarnings("unchecked")
        Map<String,JSONObject> map = (Map<String,JSONObject>)array.get(0);
        Object object = map.get("personId");
        String personID = object.toString();
        Assertions.assertEquals("1629121", personID);
    }

    @Test
    public void testGetFirstPlayer(){
        JSONArray array = JsonPath.read(document, "$..standard.*");
        @SuppressWarnings("unchecked")
        Map<String,JSONObject> map = (Map<String,JSONObject>)array.get(0);
        Object firstName = map.get("firstName");
        Object lastName = map.get("lastName");
        String fullName = firstName.toString() + lastName.toString();
        Object object = map.get("personId");
        String personID = object.toString();
        Map<Object, String> playerMap = new HashMap<>();
        playerMap.put(fullName, personID);
        Assertions.assertEquals("JaylenAdams",playerMap.entrySet().iterator().next().getKey());
        Assertions.assertEquals("1629121",playerMap.entrySet().iterator().next().getValue());
    }

    @Test
    public void testGenerateFullMap(){
        Map<String, String> playerMap = new HashMap<>();
        JSONArray array = JsonPath.read(document, "$..standard.*");
        for (int i = 0; i < array.size(); i++){
            Map<String,JSONObject> map = (Map<String,JSONObject>)array.get(i);
            Object firstName = map.get("firstName");
            Object lastName = map.get("lastName");
            String fullName = firstName.toString() + lastName.toString();
            Object object = map.get("personId");
            String playerID = object.toString();
            playerMap.put(fullName, playerID);
        }
        Assertions.assertEquals(499, playerMap.size());


    }

}
