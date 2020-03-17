package edu.bsu.cs222.nba.player.stats.model;

import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

public class SeasonGeneratorTests {



    @Test
    public void testParse(){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        SeasonGenerator generator = SeasonGenerator.create();
        JSONArray array = generator.parseYears(lebronInputStream);
        System.out.println(array);
        Assertions.assertNotNull(generator.parseYears(lebronInputStream));
    }

}
