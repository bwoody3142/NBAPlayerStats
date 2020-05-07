package edu.bsu.cs222.nba.player.stats.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.net.URL;

public class URLCreatorTest {

    @Test
    public void testPlayerListUrl() throws Exception {
        URL expected = URLCreator.createEmptyUrl().getPlayerListUrl(2018);
        Assertions.assertEquals("http://data.nba.net/data/10s/prod/v1/2018/players.json", expected.toString());
    }

    @Test
    public void testPlayerProfileUrl() throws Exception {
        URL expected = URLCreator.createEmptyUrl().getPlayerProfileUrl(2544);
        Assertions.assertEquals("http://data.nba.net/data/10s/prod/v1/2019/players/2544_profile.json",
                expected.toString());
    }

    @Test
    public void testTeamListUrl() throws Exception {
        URL expected = URLCreator.createEmptyUrl().getTeamListUrl(2018);
        Assertions.assertEquals("http://data.nba.net/data/10s/prod/v1/2018/teams.json", expected.toString());
    }

    @Test
    public void testRosterUrl() throws Exception {
        URL expected = URLCreator.createEmptyUrl().getTeamRosterUrl("lakers");
        Assertions.assertEquals("http://data.nba.net/data/10s/prod/v1/2019/teams/lakers/roster.json",
                expected.toString());
    }

    @Test
    public void testHeadshotUrl() throws Exception {
        URL expected = URLCreator.createEmptyUrl().getHeadshotUrl(2544);
        Assertions.assertEquals("https://ak-static.cms.nba.com/wp-content/uploads/headshots/nba/latest/260x190/2544.png",
                expected.toString());
    }

    @Test
    public void testLogoUrl() throws Exception {
        URL expected = URLCreator.createEmptyUrl().getLogoUrl("lal");
        Assertions.assertEquals("https://www.nba.com/.element/img/1.0/teamsites/logos/teamlogos_500x500/lal.png",
                expected.toString());
    }
}
