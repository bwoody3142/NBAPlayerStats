package edu.bsu.cs222.nba.player.stats.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

public class URLFactoryTests {
    public URLFactory.URLFactoryBuilder createLebronProfile_2019() {
        URLFactory.URLFactoryBuilder urlFactoryBuilder = new URLFactory.URLFactoryBuilder();
        urlFactoryBuilder.setYear(2019);
        urlFactoryBuilder.setPersonID("2544");
        return urlFactoryBuilder;
    }

    @Test
    public void testCreatePlayerListUrl() throws Exception {
        URLCreator urlCreator = URLCreator.createEmptyUrl();
        InputStream stream = urlCreator.createPlayerListUrl(2019);
        Assertions.assertNotNull(stream);
    }

    @Test
    public void testCreatePlayerProfileUrl() throws Exception {
        URLCreator urlCreator = URLCreator.createEmptyUrl();
        InputStream stream = urlCreator.createPlayerProfileUrl(createLebronProfile_2019());
        Assertions.assertNotNull(stream);
    }
}
