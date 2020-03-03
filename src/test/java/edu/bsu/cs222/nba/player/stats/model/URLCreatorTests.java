package edu.bsu.cs222.nba.player.stats.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.net.URL;

public class URLCreatorTests {
    public URLFactory.URLFactoryBuilder createLebronProfile_2018() {
        URLFactory.URLFactoryBuilder urlFactoryBuilder = new URLFactory.URLFactoryBuilder();
        urlFactoryBuilder.setYear(2018);
        urlFactoryBuilder.setPersonID(2544);
        return urlFactoryBuilder;
    }

    @Test
    public void testCreatePlayerListStream() throws Exception {
        URLCreator urlCreator = URLCreator.createEmptyUrl();
        InputStream stream = urlCreator.createPlayerListUrl(2018);
        Assertions.assertNotNull(stream);
    }

    @Test
    public void testCreatePlayerProfileStream() throws Exception {
        URLCreator urlCreator = URLCreator.createEmptyUrl();
        InputStream stream = urlCreator.createPlayerProfileUrl(createLebronProfile_2018());
        Assertions.assertNotNull(stream);
    }

    @Test
    public void testPlayerListUrl() throws Exception {
        URLCreator urlCreator = URLCreator.createEmptyUrl();
        URL expected = urlCreator.getPlayerListUrl(2018);
        Assertions.assertEquals("http://data.nba.net/data/10s/prod/v1/2018/players.json", expected.toString());
    }

    @Test
    public void testPlayerProfileUrl() throws Exception {
        URLCreator urlCreator = URLCreator.createEmptyUrl();
        URL expected = urlCreator.getPlayerProfileUrl(createLebronProfile_2018());
        Assertions.assertEquals("http://data.nba.net/data/10s/prod/v1/2018/players/2544_profile.json",
                expected.toString());
    }
}
