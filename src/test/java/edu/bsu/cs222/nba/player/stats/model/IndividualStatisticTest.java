package edu.bsu.cs222.nba.player.stats.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

public class IndividualStatisticTest {

    private IndividualStatistic secondIndividualStat;
    private IndividualStatistic firstIndividualStat;
    private final InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
    private final InputStream lebronInputStream2 = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
    private final PlayerStats firstPlayerStats = PlayerParser.isCareer(true).withStream(lebronInputStream).andYear(2018).parseForStats();
    private final PlayerStats secondPlayerStats = PlayerParser.isCareer(false).withStream(lebronInputStream2).andYear(2016).parseForStats();


    public void setup(Statistic statistic){
        float firstStat = firstPlayerStats.getStatsList().get(statistic.getIndex()).getStatisticValue();
        float secondStat = secondPlayerStats.getStatsList().get(statistic.getIndex()).getStatisticValue();
        firstIndividualStat = IndividualStatistic.withStatisticType(statistic).andStatisticValue(firstStat);
        secondIndividualStat = IndividualStatistic.withStatisticType(statistic).andStatisticValue(secondStat);
    }

    public void setupForEqualPlayerStatTest(Statistic statistic){
        InputStream lebronInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        PlayerStats firstPlayerStats = PlayerParser.isCareer(true).withStream(lebronInputStream).andYear(2018).parseForStats();
        InputStream lebronInputStream2 = Thread.currentThread().getContextClassLoader().getResourceAsStream("LeBronJamesStats2018.json");
        PlayerStats secondPlayerStats = PlayerParser.isCareer(true).withStream(lebronInputStream2).andYear(2018).parseForStats();
        firstIndividualStat = IndividualStatistic.withStatisticType(statistic).andStatisticValue(firstPlayerStats.getStatsList().get(statistic.getIndex()).getStatisticValue());
        secondIndividualStat = IndividualStatistic.withStatisticType(statistic).andStatisticValue(secondPlayerStats.getStatsList().get(statistic.getIndex()).getStatisticValue());
    }

    @Test
    public void testIndividualStatistic_compareTo_PlayerOneIsBetter(){
        setup(Statistic.PPG);
        int expected = firstIndividualStat.compareTo(secondIndividualStat);
        Assertions.assertEquals(1, expected);
    }

    @Test
    public void testIndividualStatistic_compareTo_PlayerTwoIsBetter(){
        setup(Statistic.FGP);
        int expected = firstIndividualStat.compareTo(secondIndividualStat);
        Assertions.assertEquals(-1, expected);
    }

    @Test
    public void testIndividualStatistic_compareTo_PlayerOneAndTwoAreEqual(){
        setupForEqualPlayerStatTest(Statistic.PPG);
        int expected = firstIndividualStat.compareTo(secondIndividualStat);
        Assertions.assertEquals(0, expected);
    }

    @Test
    public void testIndividualStatistic_generateDifference(){
        setup(Statistic.PPG);
        float expected = firstIndividualStat.generateDifference(secondIndividualStat);
        Assertions.assertEquals( 0.8f, expected);
    }
}
