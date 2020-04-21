package edu.bsu.cs222.nba.player.stats.model;

import java.util.Arrays;
import java.util.List;

public class PlayerStatComparison {

    public static ComparePlayerStatBuilder withFirstPlayerStats(PlayerStats firstPlayerStats){
        return new ComparePlayerStatBuilder(firstPlayerStats);
    }

    public static final class ComparePlayerStatBuilder {

        private final PlayerStats firstPlayerStats;
        private PlayerStats secondPlayerStats;

        public ComparePlayerStatBuilder(PlayerStats firstPlayerStats){
            this.firstPlayerStats = firstPlayerStats;
        }

        public PlayerStatComparison andSecondPlayerStats(PlayerStats secondPlayerStats){
            this.secondPlayerStats = secondPlayerStats;
            return new PlayerStatComparison(this);
        }
    }

    private final PlayerStats firstPlayerStats;
    private final PlayerStats secondPlayerStats;
    public final int PLAYER_STAT_IS_EQUAL = 0;
    public final int FIRST_PLAYER_IS_GREATER = 1;
    public final int SECOND_PLAYER_IS_GREATER = 2;
    private int flag;

    public PlayerStatComparison(ComparePlayerStatBuilder builder){
        this.firstPlayerStats = builder.firstPlayerStats;
        this.secondPlayerStats = builder.secondPlayerStats;
    }

    public float comparePPG(){
        float firstStat = firstPlayerStats.getPointsPerGame();
        float secondStat = secondPlayerStats.getPointsPerGame();
        if (firstStat > secondStat){
            flag = FIRST_PLAYER_IS_GREATER;
            return firstStat - secondStat;
        } else if (firstStat < secondStat){
            flag = SECOND_PLAYER_IS_GREATER;
            return secondStat - firstStat;
        } else {
            return PLAYER_STAT_IS_EQUAL;
        }
    }

    public float compareAPG(){
        float firstStat = firstPlayerStats.getAssistsPerGame();
        float secondStat = secondPlayerStats.getAssistsPerGame();
        if (firstStat > secondStat){
            flag = FIRST_PLAYER_IS_GREATER;
            return firstStat - secondStat;
        } else if (firstStat < secondStat){
            flag = SECOND_PLAYER_IS_GREATER;
            return secondStat - firstStat;
        } else {
            return PLAYER_STAT_IS_EQUAL;
        }
    }

    public float compareRPG(){
        float firstStat = firstPlayerStats.getReboundsPerGame();
        float secondStat = secondPlayerStats.getReboundsPerGame();
        if (firstStat > secondStat){
            flag = FIRST_PLAYER_IS_GREATER;
            return firstStat - secondStat;
        } else if (firstStat < secondStat){
            flag = SECOND_PLAYER_IS_GREATER;
            return secondStat - firstStat;
        } else {
            return PLAYER_STAT_IS_EQUAL;
        }
    }

    public float compareTOPG(){
        float firstStat = firstPlayerStats.getTurnOversPerGame();
        float secondStat = secondPlayerStats.getTurnOversPerGame();
        if (firstStat > secondStat){
            flag = FIRST_PLAYER_IS_GREATER;
            return firstStat - secondStat;
        } else if (firstStat < secondStat){
            flag = SECOND_PLAYER_IS_GREATER;
            return secondStat - firstStat;
        } else {
            return PLAYER_STAT_IS_EQUAL;
        }
    }

    public float compareSPG(){
        float firstStat = firstPlayerStats.getStealsPerGame();
        float secondStat = secondPlayerStats.getStealsPerGame();
        if (firstStat > secondStat){
            flag = FIRST_PLAYER_IS_GREATER;
            return firstStat - secondStat;
        } else if (firstStat < secondStat){
            flag = SECOND_PLAYER_IS_GREATER;
            return secondStat - firstStat;
        } else {
            return PLAYER_STAT_IS_EQUAL;
        }
    }

    public float compareBPG(){
        float firstStat = firstPlayerStats.getBlocksPerGame();
        float secondStat = secondPlayerStats.getBlocksPerGame();
        if (firstStat > secondStat){
            flag = FIRST_PLAYER_IS_GREATER;
            return firstStat - secondStat;
        } else if (firstStat < secondStat){
            flag = SECOND_PLAYER_IS_GREATER;
            return secondStat - firstStat;
        } else {
            return PLAYER_STAT_IS_EQUAL;
        }
    }

    public float compareFGP(){
        float firstStat = firstPlayerStats.getFieldGoalPercentage();
        float secondStat = secondPlayerStats.getFieldGoalPercentage();
        if (firstStat > secondStat){
            flag = FIRST_PLAYER_IS_GREATER;
            return firstStat - secondStat;
        } else if (firstStat < secondStat){
            flag = SECOND_PLAYER_IS_GREATER;
            return secondStat - firstStat;
        } else {
            return PLAYER_STAT_IS_EQUAL;
        }
    }

    public float compareFTP(){
        float firstStat = firstPlayerStats.getFreeThrowPercentage();
        float secondStat = secondPlayerStats.getFreeThrowPercentage();
        if (firstStat > secondStat){
            flag = FIRST_PLAYER_IS_GREATER;
            return firstStat - secondStat;
        } else if (firstStat < secondStat){
            flag = SECOND_PLAYER_IS_GREATER;
            return secondStat - firstStat;
        } else {
            return PLAYER_STAT_IS_EQUAL;
        }
    }

    public float compareTPM(){
        float firstStat = firstPlayerStats.getThreePointers();
        float secondStat = secondPlayerStats.getThreePointers();
        if (firstStat > secondStat){
            flag = FIRST_PLAYER_IS_GREATER;
            return firstStat - secondStat;
        } else if (firstStat < secondStat){
            flag = SECOND_PLAYER_IS_GREATER;
            return secondStat - firstStat;
        } else {
            return PLAYER_STAT_IS_EQUAL;
        }
    }

    public List<Float> getDifferencesAsList(){
        return Arrays.asList(comparePPG(),
                             compareAPG(),
                             compareRPG(),
                             compareTOPG(),
                             compareSPG(),
                             compareBPG(),
                             compareFGP(),
                             compareFTP(),
                             compareTPM());
    }

    public int getFlag() {
        return flag;
    }
}
