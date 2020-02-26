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

    private JSONArray standardArray;
    private Map<String,JSONObject> standardMap;
    private Map<String,JSONObject> teamsMap;

    @BeforeEach
    public void setup() {
        InputStream playerListInputStream  = Thread.currentThread().getContextClassLoader().getResourceAsStream("PlayerList2018.json");
        InputStream lbjInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        Object playerListDoc = Configuration.defaultConfiguration().jsonProvider().parse(playerListInputStream, "UTF-8");
        Object lbjStatsDoc = Configuration.defaultConfiguration().jsonProvider().parse(lbjInputStream, "UTF-8");
        JSONArray teamsArray = JsonPath.read(lbjStatsDoc, "$..teams.*");
        standardArray = JsonPath.read(playerListDoc, "$..standard.*");
        //noinspection unchecked
        standardMap = (Map<String,JSONObject>) standardArray.get(0);
        //noinspection unchecked
        teamsMap = (Map<String,JSONObject>) teamsArray.get(0);
    }

    @Test
    public void testGetFirstPlayerCode(){
        Object firstName = standardMap.get("firstName");
        Object lastName = standardMap.get("lastName");
        String fullName = firstName.toString() + lastName.toString();
        Assertions.assertEquals("JaylenAdams", fullName);
    }

    @Test
    public void testGetFirstPlayerID(){
        Object object = standardMap.get("personId");
        String personID = object.toString();
        Assertions.assertEquals("1629121", personID);
    }

    @Test
    public void testPutFirstPlayerInMap(){
        Object firstName = standardMap.get("firstName");
        Object lastName = standardMap.get("lastName");
        String fullName = firstName.toString() + lastName.toString();
        Object object = standardMap.get("personId");
        String personID = object.toString();
        Map<Object, String> playerMap = new HashMap<>();
        playerMap.put(fullName, personID);
        Assertions.assertEquals("JaylenAdams",playerMap.entrySet().iterator().next().getKey());
        Assertions.assertEquals("1629121",playerMap.entrySet().iterator().next().getValue());
    }

    @Test
    public void testGenerateFullMap(){
        Map<String, String> playerMap = new HashMap<>();
        for (Object unCastedMap : standardArray) {
            //Had to suppress due to the api
            @SuppressWarnings("unchecked")
            Map<String, JSONObject> map = (Map<String, JSONObject>) unCastedMap;
            Object firstName = map.get("firstName");
            Object lastName = map.get("lastName");
            String fullName = firstName.toString() + lastName.toString();
            Object object = map.get("personId");
            String playerID = object.toString();
            playerMap.put(fullName, playerID);
        }
        System.out.println(playerMap);
        Assertions.assertEquals(499, playerMap.size());
    }

    @Test
    public void testGetPPG(){
        Object object = teamsMap.get("ppg");
        String ppg = object.toString();
        Assertions.assertEquals("27.4", ppg);
    }

    @Test
    public void testGetAPG(){
        Object object = teamsMap.get("apg");
        String apg = object.toString();
        Assertions.assertEquals("8.3", apg);
    }

    @Test
    public void testGetRPG(){
        Object object = teamsMap.get("rpg");
        String rpg = object.toString();
        Assertions.assertEquals("8.5", rpg);
    }

}
