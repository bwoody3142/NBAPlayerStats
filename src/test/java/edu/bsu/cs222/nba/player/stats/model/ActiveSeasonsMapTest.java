package edu.bsu.cs222.nba.player.stats.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ActiveSeasonsMapTest {

    @Test
    public void testParse_parseYears(){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        ActiveSeasonsMap generator = ActiveSeasonsMap.create();
        Assertions.assertNotNull(generator.parseYears(lebronInputStream));
    }

    @Test
    public void testParse_getNumberOfActiveSeasons(){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        ActiveSeasonsMap generator = ActiveSeasonsMap.create();
        Assertions.assertEquals(16, generator.getNumberOfActiveSeasons(lebronInputStream));
    }

    @Test
    public void testCreateListOfActiveSeasons(){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        ActiveSeasonsMap generator = ActiveSeasonsMap.create();
        Map<String, Integer> map = generator.createActiveSeasonsMap(lebronInputStream);
        Assertions.assertEquals(2018, map.get("2018 - 2019"));
    }

    @Test
    public void testGetSeasonsAsList(){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        ActiveSeasonsMap generator = ActiveSeasonsMap.create();
        List<String> list = generator.getSeasonsAsList(lebronInputStream);
        Assertions.assertEquals("2018 - 2019", list.get(0));
    }
}
