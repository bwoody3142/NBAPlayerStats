package edu.bsu.cs222.nba.player.stats.model;

import java.text.DecimalFormat;

import static java.lang.Float.*;

public class IndividualStatistic implements Comparable<IndividualStatistic> {

    public static IndividualStatisticBuilder withStatisticType(Statistic statistic){
        return new IndividualStatisticBuilder(statistic);
    }

    public static final class IndividualStatisticBuilder {

        private final Statistic statisticType;
        private float statisticValue;

        public IndividualStatisticBuilder(Statistic statisticType){
            this.statisticType = statisticType;
        }

        public IndividualStatistic andStatisticValue(float statValue){
            this.statisticValue = statValue;
            return new IndividualStatistic(this);
        }
    }

    private final float statisticValue;
    private final Statistic statisticType;

    public IndividualStatistic(IndividualStatisticBuilder builder) {
        this.statisticType = builder.statisticType;
        this.statisticValue = builder.statisticValue;
    }

    public float getStatisticValue() {
        return roundFloat(statisticValue);
    }

    public Statistic getStatisticType() {
        return statisticType;
    }

    @Override
    public int compareTo(IndividualStatistic statistic){
        float firstStat = this.getStatisticValue();
        float secondStat = statistic.getStatisticValue();
        return compare(firstStat, secondStat);
    }

    public float generateDifference(IndividualStatistic statistic){
        float difference = this.getStatisticValue() - statistic.getStatisticValue();
        difference = roundFloat(difference);
        return Math.abs(difference);
    }

    private float roundFloat(float stat){
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return Float.parseFloat(decimalFormat.format(stat));
    }
}
