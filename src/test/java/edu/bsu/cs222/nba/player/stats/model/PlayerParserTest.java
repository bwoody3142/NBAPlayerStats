package edu.bsu.cs222.nba.player.stats.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.InputStream;

public class PlayerParserTest {

    @Test
    public void testParse_pointsPerGame() {
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        PlayerParser parser = new PlayerParser();
        PlayerStats stats = parser.parse(lebronInputStream);
        Assertions.assertEquals(27.4f, stats.getPointsPerGame(),.001);
    }


    @Test
    public void testReboundsPerGame(){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        PlayerParser parser = new PlayerParser();
        PlayerStats stats = parser.parse(lebronInputStream);
        Assertions.assertEquals(8.5f, stats.getReboundsPerGame(),.001);
    }

    @Test
    public void testAssistsPerGame(){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        PlayerParser playerParser = new PlayerParser();
        PlayerStats stats = playerParser.parse(lebronInputStream);
        Assertions.assertEquals(8.3f, stats.getAssistsPerGame(),.001);
    }
}
