package edu.bsu.cs222.nba.player.stats.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

public class URLFactoryTests {

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
