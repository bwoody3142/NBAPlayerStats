package edu.bsu.cs222.nba.player.stats.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ListOfActiveSeasonsTest {

    @Test
    public void testParse_parseYears(){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        ListOfActiveSeasons generator = ListOfActiveSeasons.create();
        Assertions.assertNotNull(generator.parseYears(lebronInputStream));
    }

    @Test
    public void testParse_getNumberOfActiveSeasons(){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        ListOfActiveSeasons generator = ListOfActiveSeasons.create();
        Assertions.assertEquals(16, generator.getNumberOfActiveSeasons(lebronInputStream));
    }

    @Test
    public void testCreateListOfActiveSeasons(){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        ListOfActiveSeasons generator = ListOfActiveSeasons.create();
        Map<String, Integer> map = generator.createListOfActiveSeasons(lebronInputStream);
        Assertions.assertEquals(2018, map.get("2018 - 2019"));
    }

    @Test
    public void testGetSeasonsAsList(){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        ListOfActiveSeasons generator = ListOfActiveSeasons.create();
        List<String> list = generator.getSeasonsAsList(lebronInputStream);
        Assertions.assertEquals("2018 - 2019", list.get(0));
    }
}
