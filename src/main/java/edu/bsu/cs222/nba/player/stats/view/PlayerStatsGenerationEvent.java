package edu.bsu.cs222.nba.player.stats.view;

import edu.bsu.cs222.nba.player.stats.model.PlayerStats;

public class PlayerStatsGenerationEvent {

    public static PlayerStatsGenerationBuilder withCareer(PlayerStats careerPlayerStats){
        return new PlayerStatsGenerationBuilder(careerPlayerStats);
    }

    public static final class PlayerStatsGenerationBuilder {

        private final PlayerStats careerPlayerStats;
        private PlayerStats seasonPlayerStats;

        public PlayerStatsGenerationBuilder(PlayerStats careerPlayerStats){
            this.careerPlayerStats = careerPlayerStats;
        }

        public PlayerStatsGenerationEvent andSeasonStats(PlayerStats seasonPlayerStats){
            this.seasonPlayerStats = seasonPlayerStats;
            return new PlayerStatsGenerationEvent(this);
        }
    }

    public PlayerStats careerPlayerStats;
    public PlayerStats seasonPlayerStats;


    public PlayerStatsGenerationEvent(PlayerStatsGenerationBuilder builder) {
        this.careerPlayerStats = builder.careerPlayerStats;
        this.seasonPlayerStats = builder.seasonPlayerStats;
    }
}
