package edu.bsu.cs222.nba.player.stats.model;

public class IndividualStatistic {

    public static IndividualStatisticBuilder withPlayerStats(PlayerStats playerStats){
        return new IndividualStatisticBuilder(playerStats);
    }

    public static final class IndividualStatisticBuilder {

        private final PlayerStats playerStats;
        private Statistic statistic;

        public IndividualStatisticBuilder(PlayerStats playerStats){
            this.playerStats = playerStats;
        }

        public IndividualStatistic andStatisticType(Statistic statistic){
            this.statistic = statistic;
            return new IndividualStatistic(this);
        }
    }

    private final PlayerStats playerStats;
    private final Statistic statistic;

    public IndividualStatistic(IndividualStatisticBuilder builder) {
        this.playerStats = builder.playerStats;
        this.statistic = builder.statistic;
    }

    public float generateStatistic(){
        return playerStats.getStatsList().get(statistic.getIndex());
    }

    public Statistic getStatistic() {
        return statistic;
    }
}
