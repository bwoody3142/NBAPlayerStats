package edu.bsu.cs222.nba.player.stats.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CurrentSeasonGeneratorTest {

    @Test
    public void testGenerateCurrentSeason(){
        CurrentSeasonGenerator generator = new CurrentSeasonGenerator();
        int currentSeason = generator.generateCurrentSeason();
        String currentSeasonAsString = String.valueOf(currentSeason);
        Assertions.assertEquals("2019", currentSeasonAsString);
    }
}
