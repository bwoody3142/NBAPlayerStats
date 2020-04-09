package edu.bsu.cs222.nba.player.stats.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ListOfTeamsTest {

    @Test
    public void testCreateFullListOfTeams() throws Exception {
        ListOfTeams listOfTeams = ListOfTeams.getNewListOfTeams();
        List<String> teamList = listOfTeams.createFullListOfTeams(2018);
        Assertions.assertNotNull(teamList);
    }
}
