package edu.bsu.cs222.nba.player.stats.model;

public class PlayerStats {

    public static PlayerStatsBuilder withPointsPerGame(float pointsPerGame) {
        return new PlayerStatsBuilder(pointsPerGame);
    }

    public static final class PlayerStatsBuilder {
        private float pointsPerGame;
        private float assistsPerGame;
        private float reboundsPerGame;

        public PlayerStatsBuilder(float pointsPerGame){
            this.pointsPerGame = pointsPerGame;
        }

        public PlayerStatsBuilder andAssistsPerGame(float assistsPerGame) {
            this.assistsPerGame = assistsPerGame;
            return this;
        }

        public PlayerStats andReboundsPerGame(float reboundsPerGame) {
            this.reboundsPerGame = reboundsPerGame;
            return new PlayerStats(this);
        }
    }

    private float pointsPerGame;
    private float assistsPerGame;
    private float reboundsPerGame;

    public PlayerStats(PlayerStatsBuilder playerStatsBuilder) {
        this.pointsPerGame = playerStatsBuilder.pointsPerGame;
        this.assistsPerGame = playerStatsBuilder.assistsPerGame;
        this.reboundsPerGame = playerStatsBuilder.reboundsPerGame;
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
}
