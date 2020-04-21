package edu.bsu.cs222.nba.player.stats.model;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class PlayerStats {

    public static PlayerStatsBuilder withPoints(float pointsPerGame) {
        return new PlayerStatsBuilder(pointsPerGame);
    }

    public static final class PlayerStatsBuilder {
        private final float pointsPerGame;
        private float assistsPerGame;
        private float reboundsPerGame;
        private float turnOversPerGame;
        private float stealsPerGame;
        private float blocksPerGame;
        private float fieldGoalPercentage;
        private float freeThrowPercentage;
        private float threePointers;

        public PlayerStatsBuilder(float pointsPerGame){
            this.pointsPerGame = pointsPerGame;
        }

        public PlayerStatsBuilder assists(float assistsPerGame) {
            this.assistsPerGame = assistsPerGame;
            return this;
        }

        public PlayerStatsBuilder rebounds(float reboundsPerGame) {
            this.reboundsPerGame = reboundsPerGame;
            return this;
        }

        public PlayerStatsBuilder turnovers(float turnOversPerGame) {
            this.turnOversPerGame = roundFloat(turnOversPerGame);
            return this;
        }

        public PlayerStatsBuilder steals(float stealsPerGame) {
            this.stealsPerGame = stealsPerGame;
            return this;
        }

        public PlayerStatsBuilder blocks(float blocksPerGame) {
            this.blocksPerGame = blocksPerGame;
            return this;
        }

        public PlayerStatsBuilder fieldGoalPercentage(float fieldGoalPercentage) {
            this.fieldGoalPercentage = fieldGoalPercentage;
            return this;
        }

        public PlayerStatsBuilder freeThrowPercentage(float freeThrowPercentage) {
            this.freeThrowPercentage = freeThrowPercentage;
            return this;
        }

        public PlayerStats andThreePointers(float threePointers) {
            this.threePointers = roundFloat(threePointers);
            return create(this);
        }

        private float roundFloat(float stat){
            DecimalFormat decimalFormat = new DecimalFormat("0.0");
            return Float.parseFloat(decimalFormat.format(stat));
        }

    }

    private final float pointsPerGame;
    private final float assistsPerGame;
    private final float reboundsPerGame;
    private final float turnOversPerGame;
    private final float stealsPerGame;
    private final float blocksPerGame;
    private final float fieldGoalPercentage;
    private final float freeThrowPercentage;
    private final float threePointers;

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

    public List<Float> getStatsList(){
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

    public float getPointsPerGame() {
        return pointsPerGame;
    }

    public float getAssistsPerGame() {
        return assistsPerGame;
    }

    public float getReboundsPerGame() {
        return reboundsPerGame;
    }

    public float getTurnOversPerGame() {
        return turnOversPerGame;
    }

    public float getStealsPerGame() {
        return stealsPerGame;
    }

    public float getBlocksPerGame() {
        return blocksPerGame;
    }

    public float getFieldGoalPercentage() {
        return fieldGoalPercentage;
    }

    public float getFreeThrowPercentage() {
        return freeThrowPercentage;
    }

    public float getThreePointers() {
        return threePointers;
    }
}
