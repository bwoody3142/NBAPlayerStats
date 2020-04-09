package edu.bsu.cs222.nba.player.stats.model;


public enum Statistic {
    PPG(0, "ppg"),
    APG(1, "apg"),
    RPG(2, "rpg"),
    GAMES_PLAYED(3, "gamesPlayed"),
    CAREER_TURNOVERS(4, "turnovers"),
    SPG(5, "spg"),
    BPG(6, "bpg"),
    FGP(7, "fgp"),
    FTP(8, "ftp"),
    TPM(9, "tpm"),
    SEASON_TOPG(10, "topg");

    public final int index;
    public final String dataSourceKey;

    Statistic(int index, String dataSourceKey) {
        this.index = index;
        this.dataSourceKey = dataSourceKey;
    }

    int getIndex(){
        return index;
    }

    String getDataSourceKey(){
        return dataSourceKey;
    }
}

