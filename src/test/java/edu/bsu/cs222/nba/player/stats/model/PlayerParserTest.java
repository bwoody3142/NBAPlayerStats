package edu.bsu.cs222.nba.player.stats.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class PlayerParserTest {

    private final boolean SEASON = false;
    private final boolean CAREER = true;
    private final List<Float> actualStatisticList = new ArrayList<>();

    private void getStats(boolean isCareer){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        PlayerParser parser = PlayerParser.isCareer(isCareer).withStream(lebronInputStream).andYear(2018);
        List<IndividualStatistic> individualStatisticList = new ArrayList<>(parser.parseForStats().getStatsList());
        individualStatisticList.forEach(statistic -> actualStatisticList.add(statistic.getStatisticValue()));
    }

    private List<Float> getActualSeasonStatsList(){
        return Arrays.asList(27.4f, 8.3f, 8.5f, 3.6f, 1.3f, 0.6f, 51f, 66.5f, 2.0f);
    }

    private List<Float> getActualCareerStatsList(){
        return Arrays.asList(27.2f, 7.2f, 7.4f, 3.5f, 1.6f, 0.8f, 50.4f, 73.6f, 1.4f);
    }

    @Test
    public void testParseForSeasonStats(){
        getStats(SEASON);
        Assertions.assertEquals(getActualSeasonStatsList(), actualStatisticList);
    }

    @Test
    public void testParseForCareerStats(){
        getStats(CAREER);
        Assertions.assertEquals(getActualCareerStatsList(), actualStatisticList);
    }
}
