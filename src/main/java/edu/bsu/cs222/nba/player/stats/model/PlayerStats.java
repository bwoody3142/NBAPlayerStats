package edu.bsu.cs222.nba.player.stats.model;

public class PlayerStats {

    public static PlayerStatsBuilder withPointsPerGame(float pointsPerGame) {
        return new PlayerStatsBuilder(pointsPerGame);
    }

    public static final class PlayerStatsBuilder {
        private float pointsPerGame;
        private float assistsPerGame;
        private float reboundsPerGame;
        private float turnOversPerGame;
        private float stealsPerGame;
        private float blocksPerGame;
        private float fieldGoalPercentage;
        private float freeThrowPercentage;
        private float threePointPercentage;

        public PlayerStatsBuilder(float pointsPerGame){
            this.pointsPerGame = pointsPerGame;
        }

        public PlayerStatsBuilder andAssistsPerGame(float assistsPerGame) {
            this.assistsPerGame = assistsPerGame;
            return this;
        }

        public PlayerStatsBuilder andReboundsPerGame(float reboundsPerGame) {
            this.reboundsPerGame = reboundsPerGame;
            return this;
        }

        public PlayerStatsBuilder andTurnOversPerGame(float turnOversPerGame) {
            this.turnOversPerGame = turnOversPerGame;
            return this;
        }

        public PlayerStatsBuilder andStealsPerGame(float stealsPerGame) {
            this.stealsPerGame = stealsPerGame;
            return this;
        }

        public PlayerStatsBuilder andBlocksPerGame(float blocksPerGame) {
            this.blocksPerGame = blocksPerGame;
            return this;
        }

        public PlayerStatsBuilder andFieldGoalPercentage(float fieldGoalPercentage) {
            this.fieldGoalPercentage = fieldGoalPercentage;
            return this;
        }

        public PlayerStatsBuilder andFreeThrowPercentage(float freeThrowPercentage) {
            this.freeThrowPercentage = freeThrowPercentage;
            return this;
        }

        public PlayerStats andThreePointPercentage(float threePointPercentage) {
            this.threePointPercentage = threePointPercentage;
            return create(this);
        }
    }

    private float pointsPerGame;
    private float assistsPerGame;
    private float reboundsPerGame;
    private float turnOversPerGame;
    private float stealsPerGame;
    private float blocksPerGame;
    private float fieldGoalPercentage;
    private float freeThrowPercentage;
    private float threePointPercentage;

    private PlayerStats (PlayerStatsBuilder builder){
        this.pointsPerGame = builder.pointsPerGame;
        this.assistsPerGame = builder.assistsPerGame;
        this.reboundsPerGame = builder.reboundsPerGame;
        this.turnOversPerGame = builder.turnOversPerGame;
        this.stealsPerGame = builder.stealsPerGame;
        this.blocksPerGame = builder.blocksPerGame;
        this.fieldGoalPercentage = builder.fieldGoalPercentage;
        this.freeThrowPercentage = builder.freeThrowPercentage;
        this.threePointPercentage = builder.threePointPercentage;
    }

    public static PlayerStats create(PlayerStatsBuilder builder){
        return new PlayerStats(builder);
    }

    public float getPointsPerGame() {
        return pointsPerGame;
    }

    public float getAssistsPerGame() {
        return assistsPerGame;
    }

    public float getReboundsPerGame() { return reboundsPerGame; }

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

    public float getThreePointPercentage() {
        return threePointPercentage;
    }

    @Override
    public String toString() {
        return  "PPG: " + pointsPerGame + "\n" +
                "APG: " + assistsPerGame + "\n" +
                "RPG: " + reboundsPerGame + "\n" +
                "TOPG: " + turnOversPerGame + "\n" +
                "SPG: " + stealsPerGame + "\n" +
                "BPG: " + blocksPerGame + "\n" +
                "FGP: " + fieldGoalPercentage + "%\n" +
                "FTP: " + freeThrowPercentage + "%\n" +
                "TPP: " + threePointPercentage  + "%";
    }
}
