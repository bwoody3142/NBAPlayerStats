package edu.bsu.cs222.nba.player.stats.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

public class PlayerParserTest {

    @Test
    public void testPointsPerGame(){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        PlayerParser playerParser = new PlayerParser();
        double actual = playerParser.parse(lebronInputStream);
        Assertions.assertEquals(27.4, actual);
    }
}
