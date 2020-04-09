package edu.bsu.cs222.nba.player.stats.model;

import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class TeamRosterTest {

    private JSONArray lakers() throws Exception {
        return TeamRoster.createTeamRoster("lakers").getPersonIdArray();
    }

    @Test
    public void testRoster_NotNull() throws Exception {
        Assertions.assertNotNull(lakers());
    }

    @Test
    public void testRoster_sizeOfRoster() throws Exception {
        Assertions.assertEquals(17, lakers().size());
    }

    @Test
    public void testRoster_getFirstPersonID() throws Exception {
        Assertions.assertEquals("1628961", lakers().get(0));
    }

    @Test
    public void testRoster_getRoster() throws Exception {
        TeamRoster roster = TeamRoster.createTeamRoster("lakers");
        Map<String, String> rosterMap = roster.createRoster();
        Assertions.assertEquals("LeBron James", rosterMap.get("2544"));

    }
}
