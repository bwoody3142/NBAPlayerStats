package edu.bsu.cs222.nba.player.stats.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class PlayerInfoTest {

    @Test
    public void testPlayerInfo(){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("PlayerList2018.json");
        PlayerInfoParser parser = PlayerInfoParser.withStream(lebronInputStream).andPersonID(2544);
        Assertions.assertEquals(getActualPlayerInfo(), parser.parseForPlayerInfo().getPlayerInfoAsList());
    }

    private List<Object> getActualPlayerInfo() {
        return Arrays.asList("LeBron James", 23,"F", 15, 6, 8, 250);
    }
}
