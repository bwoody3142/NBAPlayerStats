package edu.bsu.cs222.nba.player.stats.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.net.URL;

public class URLCreatorTests {

    @Test
    public void testCreatePlayerListStream() throws Exception {
        URLCreator urlCreator = URLCreator.createEmptyUrl();
        InputStream stream = urlCreator.createPlayerListStream(2018);
        Assertions.assertNotNull(stream);
    }

    @Test
    public void testCreatePlayerProfileStream() throws Exception {
        URLCreator urlCreator = URLCreator.createEmptyUrl();
        InputStream stream = urlCreator.createPlayerProfileStream(2544);
        Assertions.assertNotNull(stream);
    }

    @Test
    public void testCreateTeamListStream() throws Exception {
        URLCreator urlCreator = URLCreator.createEmptyUrl();
        InputStream stream = urlCreator.createTeamListStream(2018);
        Assertions.assertNotNull(stream);
    }

    @Test
    public void testCreateRosterStream() throws Exception {
        URLCreator urlCreator = URLCreator.createEmptyUrl();
        InputStream stream = urlCreator.createTeamRosterStream("lakers");
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
        URL expected = urlCreator.getPlayerProfileUrl(2544);
        Assertions.assertEquals("http://data.nba.net/data/10s/prod/v1/2018/players/2544_profile.json",
                expected.toString());
    }

    @Test
    public void testTeamListUrl() throws Exception {
        URLCreator urlCreator = URLCreator.createEmptyUrl();
        URL expected = urlCreator.getTeamListUrl(2018);
        Assertions.assertEquals("http://data.nba.net/data/10s/prod/v1/2018/teams.json", expected.toString());
    }

    @Test
    public void testRosterUrl() throws Exception {
        URLCreator urlCreator = URLCreator.createEmptyUrl();
        URL expected = urlCreator.getTeamRosterUrl("lakers");
        Assertions.assertEquals("http://data.nba.net/data/10s/prod/v1/2019/teams/lakers/roster.json",
                expected.toString());
    }
}
