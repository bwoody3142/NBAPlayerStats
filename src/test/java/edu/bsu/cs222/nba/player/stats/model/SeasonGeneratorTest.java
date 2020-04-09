package edu.bsu.cs222.nba.player.stats.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

public class SeasonGeneratorTest {

    @Test
    public void testParse_parseYears(){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        SeasonGenerator generator = SeasonGenerator.create();
        Assertions.assertNotNull(generator.parseYears(lebronInputStream));
    }

    @Test
    public void testParse_getNumberOfActiveSeasons(){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        SeasonGenerator generator = SeasonGenerator.create();
        Assertions.assertEquals(16, generator.getNumberOfActiveSeasons(lebronInputStream));
    }
}
