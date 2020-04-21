package edu.bsu.cs222.nba.player.stats.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

public class PlayerStatComparisonTest {

    private PlayerStatComparison playerStatComparison;


    public void setup(Statistic statistic){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        PlayerStats firstPlayerStats = PlayerParser.isCareer(true).withStream(lebronInputStream).andYear(2018).parseForStats();
        InputStream lebronInputStream2 = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        PlayerStats secondPlayerStats = PlayerParser.isCareer(false).withStream(lebronInputStream2).andYear(2016).parseForStats();
        IndividualStatistic firstIndividualStat = IndividualStatistic.withPlayerStats(firstPlayerStats).andStatisticType(statistic);
        IndividualStatistic secondIndividualStat = IndividualStatistic.withPlayerStats(secondPlayerStats).andStatisticType(statistic);
        playerStatComparison = PlayerStatComparison.withFirstIndividualStats(firstIndividualStat).andSecondPlayerStats(secondIndividualStat);
    }

    public void setupForEqualPlayerStatTest(Statistic statistic){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        PlayerStats firstPlayerStats = PlayerParser.isCareer(true).withStream(lebronInputStream).andYear(2018).parseForStats();
        InputStream lebronInputStream2 = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        PlayerStats secondPlayerStats = PlayerParser.isCareer(true).withStream(lebronInputStream2).andYear(2018).parseForStats();
        IndividualStatistic firstIndividualStat = IndividualStatistic.withPlayerStats(firstPlayerStats).andStatisticType(statistic);
        IndividualStatistic secondIndividualStat = IndividualStatistic.withPlayerStats(secondPlayerStats).andStatisticType(statistic);
        playerStatComparison = PlayerStatComparison.withFirstIndividualStats(firstIndividualStat).andSecondPlayerStats(secondIndividualStat);
    }

    @Test
    public void testCompareStat_getFlag_playerOneIsBetter(){
        setup(Statistic.PPG);
        Assertions.assertEquals(1, playerStatComparison.getFlag());
    }

    @Test
    public void testCompareStat_getFlag_playerTwoIsBetter(){
        setup(Statistic.FGP);
        Assertions.assertEquals(2, playerStatComparison.getFlag());
    }

    @Test
    public void testCompareStat_getFlag_playerOneAndTwoAreEqual(){
        setupForEqualPlayerStatTest(Statistic.PPG);
        Assertions.assertEquals(0, playerStatComparison.getFlag());
    }
}
