package edu.bsu.cs222.nba.player.stats.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

public class PlayerParserTest {


    private PlayerStats getPlayerStats(){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        PlayerParser parser = PlayerParser.withStream(lebronInputStream).andYear(2018);
        return parser.parse();
    }

    @Test
    public void testParse_pointsPerGame() {
        Assertions.assertEquals(27.4f, getPlayerStats().getPointsPerGame(),.001);
    }

    @Test
    public void testParse_assistsPerGame(){
        Assertions.assertEquals(8.3f, getPlayerStats().getAssistsPerGame(),.001);
    }

    @Test
    public void testParse_reboundsPerGame(){
        Assertions.assertEquals(8.5f, getPlayerStats().getReboundsPerGame(),.001);
    }
}
