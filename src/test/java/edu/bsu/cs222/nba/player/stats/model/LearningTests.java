package edu.bsu.cs222.nba.player.stats.model;
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

public class LearningTests {

    private Object playerListDoc;
    private Object lbjStatsDoc;

    @BeforeEach
    public void setup() {
        InputStream playerListInputStream  = Thread.currentThread().getContextClassLoader().getResourceAsStream("PlayerList2018.json");
        InputStream lbjInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        playerListDoc = Configuration.defaultConfiguration().jsonProvider().parse(playerListInputStream, "UTF-8");
        lbjStatsDoc = Configuration.defaultConfiguration().jsonProvider().parse(lbjInputStream, "UTF-8");
    }

    @Test
    public void testGetFirstPlayerCode(){
        JSONArray array = JsonPath.read(playerListDoc, "$..standard.*");
        @SuppressWarnings("unchecked")
        Map<String,JSONObject> map = (Map<String,JSONObject>)array.get(0);
        Object firstName = map.get("firstName");
        Object lastName = map.get("lastName");
        String fullName = firstName.toString() + lastName.toString();
        Assertions.assertEquals("JaylenAdams", fullName);
    }

    @Test
    public void testGetFirstPlayerID(){
        JSONArray array = JsonPath.read(playerListDoc, "$..standard.*");
        @SuppressWarnings("unchecked")
        Map<String,JSONObject> map = (Map<String,JSONObject>)array.get(0);
        Object object = map.get("personId");
        String personID = object.toString();
        Assertions.assertEquals("1629121", personID);
    }

    @Test
    public void testGetFirstPlayer(){
        JSONArray array = JsonPath.read(playerListDoc, "$..standard.*");
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
        JSONArray array = JsonPath.read(playerListDoc, "$..standard.*");
        for (Object unCastedMap : array) {
            //Had to suppress due to the api
            @SuppressWarnings("unchecked") Map<String, JSONObject> map = (Map<String, JSONObject>) unCastedMap;
            Object firstName = map.get("firstName");
            Object lastName = map.get("lastName");
            String fullName = firstName.toString() + lastName.toString();
            Object object = map.get("personId");
            String playerID = object.toString();
            playerMap.put(fullName, playerID);
        }
        Assertions.assertEquals(499, playerMap.size());
    }

    @Test
    public void testGetPPG(){
        JSONArray array = JsonPath.read(lbjStatsDoc, "$..teams.*");
        @SuppressWarnings("unchecked")
        Map<String,JSONObject> map = (Map<String,JSONObject>)array.get(0);
        Object object = map.get("ppg");
        String ppg = object.toString();
        Assertions.assertEquals("27.4", ppg);
    }

    @Test
    public void testGetAPG(){
        JSONArray array = JsonPath.read(lbjStatsDoc, "$..teams.*");
        @SuppressWarnings("unchecked")
        Map<String,JSONObject> map = (Map<String,JSONObject>)array.get(0);
        Object object = map.get("apg");
        String apg = object.toString();
        Assertions.assertEquals("8.3", apg);
    }

    @Test
    public void testGetRPG(){
        JSONArray array = JsonPath.read(lbjStatsDoc, "$..teams.*");
        @SuppressWarnings("unchecked")
        Map<String,JSONObject> map = (Map<String,JSONObject>)array.get(0);
        Object object = map.get("rpg");
        String rpg = object.toString();
        Assertions.assertEquals("8.5", rpg);
    }

}
