package edu.bsu.cs222.nba.player.stats.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

public class PlayerParserTest {


    private List<Float> getSeasonStats(){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        PlayerParser parser = PlayerParser.withStream(lebronInputStream).andYear(2018);
        return parser.parseForSeasonStats().getSeasonStatsList();
    }

    private List<Float> getCareerStats(){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        PlayerParser parser = PlayerParser.withStream(lebronInputStream).andYear(2018);
        return parser.parseForCareerStats().getCareerStatsList();
    }

    private List<Float> getActualSeasonStatsList(){
        return Arrays.asList(27.4f, 8.3f, 8.5f, 3.6f, 1.3f, 0.6f, 51f, 66.5f, 2.0f);
    }

    private List<Float> getActualCareerStatsList(){
        return Arrays.asList(27.2f, 7.2f, 7.4f, 3.5f, 1.6f, 0.8f, 50.4f, 73.6f, 1.4f);
    }

    @Test
    public void testParseForSeasonStats(){
        Assertions.assertEquals(getActualSeasonStatsList(), getSeasonStats());
    }

    @Test
    public void testParseForCareerStats(){
        Assertions.assertEquals(getActualCareerStatsList(), getCareerStats());
    }
}
