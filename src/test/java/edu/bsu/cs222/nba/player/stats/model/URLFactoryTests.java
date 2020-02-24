package edu.bsu.cs222.nba.player.stats.model;

import com.jayway.jsonpath.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

public class URLFactoryTests {

    private Object playerListDoc;
    private Object lbjStatsDoc;

    @BeforeEach
    public void setup() {
        InputStream playerListInputStream  = Thread.currentThread().getContextClassLoader().getResourceAsStream("PlayerList2018.json");
        InputStream lbjInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        playerListDoc = Configuration.defaultConfiguration().jsonProvider().parse(playerListInputStream, "UTF-8");
        lbjStatsDoc = Configuration.defaultConfiguration().jsonProvider().parse(lbjInputStream, "UTF-8");
    }

    @Test
    public void testCreatePlayerListUrl() throws Exception {
        URLFactory urlFactory = new URLFactory();
        InputStream stream = urlFactory.createPlayerListUrl("2019");
        Assertions.assertNotNull(stream);
    }

    @Test
    public void testCreatePlayerProfileUrl() throws Exception {
        URLFactory urlFactory = new URLFactory();
        InputStream stream = urlFactory.createPlayerProfileUrl("2019", "2544");
        Assertions.assertNotNull(stream);
    }
}
