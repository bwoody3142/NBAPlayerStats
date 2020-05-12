package edu.bsu.cs222.nba.player.stats.model;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.io.InputStream;

public class PlayerParser {

    public static PlayerParserBuilder isCareer(boolean isCareer) {
        return new PlayerParserBuilder(isCareer);
    }

    public static final class PlayerParserBuilder{
        private final boolean isCareer;
        private InputStream stream;
        private Integer year;

        public PlayerParserBuilder(boolean isCareer){
            this.isCareer = isCareer;
        }

        public PlayerParserBuilder withStream(InputStream stream){
            this.stream = stream;
            return this;
        }

        public PlayerParser andYear(Integer year) {
            this.year = year;
            return new PlayerParser(this);
        }
    }
    private final InputStream stream;
    private final Integer year;
    private final JSONArray array = new JSONArray();
    private final boolean isCareer;

    public PlayerParser(PlayerParserBuilder builder) {
        this.isCareer = builder.isCareer;
        this.stream = builder.stream;
        this.year = builder.year;
    }

    public PlayerStats parseForStats(){
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(stream, "UTF-8");
        for (Statistic statistic : Statistic.values()) {
            array.add(JsonPath.read(document, makeStatsQuery(statistic.getDataSourceKey())));
        }
        return buildStats();
    }

    private PlayerStats buildStats() {
        return PlayerStats.withPoints(accessIndividualStatistic(Statistic.PPG.getIndex()))
                .assists(accessIndividualStatistic(Statistic.APG.getIndex()))
                .rebounds(accessIndividualStatistic(Statistic.RPG.getIndex()))
                .turnovers(divideByGamesPlayed(accessIndividualStatistic(Statistic.TOPG.getIndex())))
                .steals(accessIndividualStatistic(Statistic.SPG.getIndex()))
                .blocks(accessIndividualStatistic(Statistic.BPG.getIndex()))
                .fieldGoalPercentage(accessIndividualStatistic(Statistic.FGP.getIndex()))
                .freeThrowPercentage(accessIndividualStatistic(Statistic.FTP.getIndex()))
                .andThreePointers(divideByGamesPlayed(accessIndividualStatistic(Statistic.TPM.getIndex())));
    }

    private String makeStatsQuery(String stat){
        if(isCareer){
            return "$..careerSummary." + stat;
        } else {
            return "$..season[?(@.seasonYear==" + year + ")].total." + stat;
        }
    }

    private IndividualStatistic accessIndividualStatistic(Integer index){
        float statValue = Float.parseFloat(((JSONArray) array.get(index)).get(0).toString());
        Statistic[] statisticType = Statistic.values();
        return IndividualStatistic.withStatisticType(statisticType[index]).andStatisticValue(statValue);
    }

    private IndividualStatistic divideByGamesPlayed(IndividualStatistic individualStatistic){
        Statistic statisticType = individualStatistic.getStatisticType();
        float statAsFloat = individualStatistic.getStatisticValue();
        float gamesPlayedAsFloat = accessIndividualStatistic(Statistic.GAMES_PLAYED.getIndex()).getStatisticValue();
        float resultAsFloat = statAsFloat / gamesPlayedAsFloat;
        return IndividualStatistic.withStatisticType(statisticType).andStatisticValue(resultAsFloat);
    }
}
