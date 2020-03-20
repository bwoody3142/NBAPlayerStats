package edu.bsu.cs222.nba.player.stats.model;

import com.google.common.cache.AbstractCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class PlayerParserTest {


    private List<Stat> getStatsList(){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        PlayerParser parser = PlayerParser.withStream(lebronInputStream).andYear(2018);
        return parser.parse().getListOfStats();
    }

    private List<Stat> getActualList(){
        return Arrays.asList(Stat.createStat(27.4f), Stat.createStat(8.3f),
                Stat.createStat(8.5f), Stat.createStat(3.6f), Stat.createStat(1.3f),
                Stat.createStat(0.6f), Stat.createStat(51f), Stat.createStat(66.5f), Stat.createStat(111f));
    }

    @Test
    public void testParse(){
        System.out.println(getStatsList());
        Assertions.assertEquals(getActualList().toString(), getStatsList().toString());
    }
}
