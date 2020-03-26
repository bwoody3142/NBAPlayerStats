package edu.bsu.cs222.nba.player.stats.model;

import com.google.common.cache.AbstractCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class PlayerParserTest {


    private List<Float> getStatsList(){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        PlayerParser parser = PlayerParser.withStream(lebronInputStream).andYear(2018);
        return parser.parse().getListOfStats();
    }

    private List<Float> getActualList(){
        return Arrays.asList(27.4f, 8.3f, 8.5f, 3.6f, 1.3f, 0.6f, 51f, 66.5f, 111f);
    }

    @Test
    public void testParse(){
        System.out.println(getStatsList());
        Assertions.assertEquals(getActualList().toString(), getStatsList().toString());
    }
}
