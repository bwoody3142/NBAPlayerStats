package edu.bsu.cs222.nba.player.stats.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class ListOfPlayersTests {

    @Test
    public void testCreateFullListOfPlayers() throws Exception {
        ListOfPlayers listOfPlayers = ListOfPlayers.createEmptyListOfPlayers();
        Map<String,String> playerMap = listOfPlayers.createFullListOfPlayers();
        Assertions.assertNotNull(playerMap);
    }
}
