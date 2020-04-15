package edu.bsu.cs222.nba.player.stats.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

public class TeamParserTest {


    private Team getLakers(){
        InputStream teamListStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("TeamList2018.json");
        TeamParser parser = TeamParser.withStream(teamListStream).andFullTeamName("Los Angeles Lakers");
        return parser.parse();
    }

    @Test
    public void testParse_abreviation(){
        Assertions.assertEquals("lal", getLakers().getAbbreviation());
    }

    @Test
    public void testParse_urlName(){
        Assertions.assertEquals("lakers", getLakers().getUrlName());
    }
}
