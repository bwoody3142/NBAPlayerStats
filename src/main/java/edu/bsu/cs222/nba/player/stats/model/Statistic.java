package edu.bsu.cs222.nba.player.stats.model;


public enum Statistic {
    PPG(0, "ppg"),
    APG(1, "apg"),
    RPG(2, "rpg"),
    TOPG(3, "turnovers"),
    SPG(4, "spg"),
    BPG(5, "bpg"),
    FGP(6, "fgp"),
    FTP(7, "ftp"),
    TPM(8, "tpm"),
    GAMES_PLAYED(9, "gamesPlayed");

    private final int index;
    private final String dataSourceKey;

    Statistic(int index, String dataSourceKey) {
        this.index = index;
        this.dataSourceKey = dataSourceKey;
    }

    public int getIndex(){
        return index;
    }

    public String getDataSourceKey(){
        return dataSourceKey;
    }
}

