package edu.bsu.cs222.nba.player.stats.view;

import edu.bsu.cs222.nba.player.stats.model.PlayerInfo;
import edu.bsu.cs222.nba.player.stats.model.PlayerStats;

public class ResultGenerationEvent {

    public static ResultGenerationEventBuilder withCareerStats(PlayerStats careerPlayerStats){
        return new ResultGenerationEventBuilder(careerPlayerStats);
    }

    public static final class ResultGenerationEventBuilder {

        private final PlayerStats careerPlayerStats;
        private PlayerStats seasonPlayerStats;
        private PlayerInfo playerInfo;

        public ResultGenerationEventBuilder(PlayerStats careerPlayerStats){
            this.careerPlayerStats = careerPlayerStats;
        }

        public ResultGenerationEventBuilder seasonStats(PlayerStats seasonPlayerStats){
            this.seasonPlayerStats = seasonPlayerStats;
            return this;
        }

        public ResultGenerationEvent andPlayerInfo(PlayerInfo playerInfo){
            this.playerInfo = playerInfo;
            return new ResultGenerationEvent(this);
        }
    }

    public PlayerStats careerPlayerStats;
    public PlayerStats seasonPlayerStats;
    public PlayerInfo playerInfo;


    public ResultGenerationEvent(ResultGenerationEventBuilder builder) {
        this.careerPlayerStats = builder.careerPlayerStats;
        this.seasonPlayerStats = builder.seasonPlayerStats;
        this.playerInfo = builder.playerInfo;
    }
}
