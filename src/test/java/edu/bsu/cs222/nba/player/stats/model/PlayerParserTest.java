package edu.bsu.cs222.nba.player.stats.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.InputStream;

public class PlayerParserTest {

    @Test
    public void testParse_pointsPerGame() {
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        PlayerParser parser = PlayerParser.withStream(lebronInputStream).andYear(2018);
        PlayerStats stats = parser.parse();
        Assertions.assertEquals(27.4f, stats.getPointsPerGame(),.001);
    }

    @Test
    public void testParse_assistsPerGame(){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        PlayerParser parser = PlayerParser.withStream(lebronInputStream).andYear(2018);
        PlayerStats stats = parser.parse();
        Assertions.assertEquals(8.3f, stats.getAssistsPerGame(),.001);
    }

    @Test
    public void testParse_reboundsPerGame(){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        PlayerParser parser = PlayerParser.withStream(lebronInputStream).andYear(2018);
        PlayerStats stats = parser.parse();
        Assertions.assertEquals(8.5f, stats.getReboundsPerGame(),.001);
    }
}
