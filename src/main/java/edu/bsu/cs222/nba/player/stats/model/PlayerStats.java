package edu.bsu.cs222.nba.player.stats.model;

public class PlayerStats {

    private float ppg;
    private float apg;
    private float rpg;

    public PlayerStats(float ppg, float apg, float rpg) {
        this.ppg = ppg;
        this.apg = apg;
        this.rpg = rpg;
    }

    public float getPointsPerGame() {
        return ppg;
    }

    public float getAssistsPerGame() {
        return apg;
    }

    public float getReboundsPerGame() {
        return rpg;
    }
}
