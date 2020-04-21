package edu.bsu.cs222.nba.player.stats.model;

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

    public PlayerStatComparison(ComparePlayerStatBuilder builder){
        this.firstIndividualStat = builder.firstIndividualStat;
        this.secondIndividualStat = builder.secondIndividualStats;
        this.flag = PLAYER_STAT_IS_EQUAL;
        compareStat();
    }

    private void compareStat(){
        float firstStat = firstIndividualStat.generateStatistic();
        float secondStat = secondIndividualStat.generateStatistic();
        if (firstIndividualStat.getStatistic().equals(Statistic.TURNOVERS)){
            if (firstStat < secondStat){
                flag = FIRST_PLAYER_IS_BETTER;
            } else if (firstStat > secondStat){
                flag = SECOND_PLAYER_IS_BETTER;
            }
        } else {
            if (firstStat > secondStat){
                flag = FIRST_PLAYER_IS_BETTER;
            } else if (firstStat < secondStat){
                flag = SECOND_PLAYER_IS_BETTER;
            }
        }
    }

    public int getFlag() {
        return flag;
    }
}
