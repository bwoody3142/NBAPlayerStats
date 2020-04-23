package edu.bsu.cs222.nba.player.stats.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class ListOfPlayersTest {

    @Test
    public void testCreateFullListOfPlayers() throws Exception {
        PlayerMap listOfPlayers = PlayerMap.createEmptyPlayerMap();
        Map<String,Integer> playerMap = listOfPlayers.createMapOfPlayersWithID();
        Assertions.assertNotNull(playerMap);
    }
}