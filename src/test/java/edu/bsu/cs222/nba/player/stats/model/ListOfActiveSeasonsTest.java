package edu.bsu.cs222.nba.player.stats.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

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
}
