package edu.bsu.cs222.nba.player.stats.model;

import java.util.Arrays;
import java.util.List;

public class PlayerStats {

    public static PlayerStatsBuilder withPoints(IndividualStatistic pointsPerGame) {
        return new PlayerStatsBuilder(pointsPerGame);
    }

    public static final class PlayerStatsBuilder {
        private final IndividualStatistic pointsPerGame;
        private IndividualStatistic assistsPerGame;
        private IndividualStatistic reboundsPerGame;
        private IndividualStatistic turnOversPerGame;
        private IndividualStatistic stealsPerGame;
        private IndividualStatistic blocksPerGame;
        private IndividualStatistic fieldGoalPercentage;
        private IndividualStatistic freeThrowPercentage;
        private IndividualStatistic threePointers;

        public PlayerStatsBuilder(IndividualStatistic pointsPerGame){
            this.pointsPerGame = pointsPerGame;
        }

        public PlayerStatsBuilder assists(IndividualStatistic assistsPerGame) {
            this.assistsPerGame = assistsPerGame;
            return this;
        }

        public PlayerStatsBuilder rebounds(IndividualStatistic reboundsPerGame) {
            this.reboundsPerGame = reboundsPerGame;
            return this;
        }

        public PlayerStatsBuilder turnovers(IndividualStatistic turnOversPerGame) {
            this.turnOversPerGame = turnOversPerGame;
            return this;
        }

        public PlayerStatsBuilder steals(IndividualStatistic stealsPerGame) {
            this.stealsPerGame = stealsPerGame;
            return this;
        }

        public PlayerStatsBuilder blocks(IndividualStatistic blocksPerGame) {
            this.blocksPerGame = blocksPerGame;
            return this;
        }

        public PlayerStatsBuilder fieldGoalPercentage(IndividualStatistic fieldGoalPercentage) {
            this.fieldGoalPercentage = fieldGoalPercentage;
            return this;
        }

        public PlayerStatsBuilder freeThrowPercentage(IndividualStatistic freeThrowPercentage) {
            this.freeThrowPercentage = freeThrowPercentage;
            return this;
        }

        public PlayerStats andThreePointers(IndividualStatistic threePointers) {
            this.threePointers = threePointers;
            return create(this);
        }

    }

    private final IndividualStatistic pointsPerGame;
    private final IndividualStatistic assistsPerGame;
    private final IndividualStatistic reboundsPerGame;
    private final IndividualStatistic turnOversPerGame;
    private final IndividualStatistic stealsPerGame;
    private final IndividualStatistic blocksPerGame;
    private final IndividualStatistic fieldGoalPercentage;
    private final IndividualStatistic freeThrowPercentage;
    private final IndividualStatistic threePointers;

    private PlayerStats (PlayerStatsBuilder builder){
        this.pointsPerGame = builder.pointsPerGame;
        this.assistsPerGame = builder.assistsPerGame;
        this.reboundsPerGame = builder.reboundsPerGame;
        this.turnOversPerGame = builder.turnOversPerGame;
        this.stealsPerGame = builder.stealsPerGame;
        this.blocksPerGame = builder.blocksPerGame;
        this.fieldGoalPercentage = builder.fieldGoalPercentage;
        this.freeThrowPercentage = builder.freeThrowPercentage;
        this.threePointers = builder.threePointers;
    }

    public static PlayerStats create(PlayerStatsBuilder builder){
        return new PlayerStats(builder);
    }

    public List<IndividualStatistic> getStatsList(){
        return Arrays.asList(pointsPerGame,
                assistsPerGame,
                reboundsPerGame,
                turnOversPerGame,
                stealsPerGame,
                blocksPerGame,
                fieldGoalPercentage,
                freeThrowPercentage,
                threePointers);
    }

    public IndividualStatistic getPointsPerGame() {
        return pointsPerGame;
    }

    public IndividualStatistic getAssistsPerGame() {
        return assistsPerGame;
    }

    public IndividualStatistic getReboundsPerGame() {
        return reboundsPerGame;
    }

    public IndividualStatistic getTurnOversPerGame() {
        return turnOversPerGame;
    }

    public IndividualStatistic getStealsPerGame() {
        return stealsPerGame;
    }

    public IndividualStatistic getBlocksPerGame() {
        return blocksPerGame;
    }

    public IndividualStatistic getFieldGoalPercentage() {
        return fieldGoalPercentage;
    }

    public IndividualStatistic getFreeThrowPercentage() {
        return freeThrowPercentage;
    }

    public IndividualStatistic getThreePointers() {
        return threePointers;
    }
}
