package edu.bsu.cs222.nba.player.stats.model;

import java.text.DecimalFormat;

public class PlayerStatComparison {

    public static ComparePlayerStatBuilder withFirstIndividualStats(IndividualStatistic stat){
        return new ComparePlayerStatBuilder(stat);
    }

    public static final class ComparePlayerStatBuilder {

        private final IndividualStatistic firstIndividualStat;
        private IndividualStatistic secondIndividualStats;

        public ComparePlayerStatBuilder(IndividualStatistic stat){
            this.firstIndividualStat = stat;
        }

        public PlayerStatComparison andSecondPlayerStats(IndividualStatistic stat){
            this.secondIndividualStats = stat;
            return new PlayerStatComparison(this);
        }
    }

    private final IndividualStatistic firstIndividualStat;
    private final IndividualStatistic secondIndividualStat;
    public final int PLAYER_STAT_IS_EQUAL = 0;
    public final int FIRST_PLAYER_IS_BETTER = 1;
    public final int SECOND_PLAYER_IS_BETTER = 2;
    private int flag;
    private float difference;

    public PlayerStatComparison(ComparePlayerStatBuilder builder){
        this.firstIndividualStat = builder.firstIndividualStat;
        this.secondIndividualStat = builder.secondIndividualStats;
        compareStat();
    }

    private void compareStat(){
        if (firstIndividualStat.getStatistic().equals(Statistic.TURNOVERS)){
            float firstStat = firstIndividualStat.generateStatistic();
            float secondStat = secondIndividualStat.generateStatistic();
            if (firstStat < secondStat){
                flag = FIRST_PLAYER_IS_BETTER;
                difference = secondStat - firstStat;
            } else if (firstStat > secondStat){
                flag = SECOND_PLAYER_IS_BETTER;
                difference = firstStat - secondStat;
            } else {
                difference = PLAYER_STAT_IS_EQUAL;
            }
        } else {
            float firstStat = firstIndividualStat.generateStatistic();
            float secondStat = secondIndividualStat.generateStatistic();
            if (firstStat > secondStat){
                flag = FIRST_PLAYER_IS_BETTER;
                difference = firstStat - secondStat;
            } else if (firstStat < secondStat){
                flag = SECOND_PLAYER_IS_BETTER;
                difference = secondStat - firstStat;
            } else {
                flag = PLAYER_STAT_IS_EQUAL;
                difference = 0;
            }
        }
    }

    public int getFlag() {
        return flag;
    }

    public float getDifference() {
        return roundFloat(difference);
    }

    private float roundFloat(float stat){
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return Float.parseFloat(decimalFormat.format(stat));
    }
}
